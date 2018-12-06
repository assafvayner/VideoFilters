import javax.swing.*;
import java.util.ArrayList;


public class SkinFilter implements PixelFilter {

    final short[][] kernel = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};

    private static short[][] out;
    private static short[][] out2;
    int[] colors = {65535, 16776960, 16711935, 200, 16663850,16753920};

    private int kernelWeight;

    private short red = 186;
    private short green = 108;
    private short blue = 73;

    private double THRESHOLD = 80;
    private static final int THRESHOLD2 = 254;

    private int numClusters;

    private Cluster[] clusters;

    public SkinFilter() {
        numClusters = Integer.parseInt( JOptionPane.showInputDialog("enter a number"));
        clusters = new Cluster[numClusters];
        for (int i = 0; i < clusters.length; i++) {
            clusters[i] = new Cluster();
        }
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        int[][] pixels2d = PixelLib.convertTo2dArray(pixels, width, height);
        PixelLib.ColorComponents2d img = PixelLib.getColorComponents2d(pixels2d);

        kernelWeight = sumOf(kernel);
        if (out == null) {  // initialize to start, then re-use
            out = new short[height][width];
            out2 = new short[height][width];
        }

        performThreshold(img, out);
        performBlur(out, out2);
        performSecondThreshold(out2);

        initializeClusters();

        kMeans();

        PixelLib.ColorComponents2d image = new PixelLib.ColorComponents2d(width, height);
        image.clear();
        colorPixelsBasedOnClusters(image);

        pixels = PixelLib.combineColorComponents(image);

        return pixels;
    }

    private void colorPixelsBasedOnClusters(PixelLib.ColorComponents2d img) {
        for (int c = 0; c < clusters.length; c++) {
            for (Point p : clusters[c].getPoints()) {
                changeColor(img, p.getX(), p.getY(), c);
            }
        }
    }

    private void changeColor(PixelLib.ColorComponents2d img, int row, int col, int colorIndex) {
        img.red[row][col] = (short) (colors[colorIndex]/65536);
        img.green[row][col] = (short)((colors[colorIndex]- img.red[row][col])/256);
        img.blue[row][col] = (short)((colors[colorIndex]- img.red[row][col] - img.green[row][col]));
    }

    private void kMeans() {
        boolean flag = false;//true if no change in centers
        Point curClusterCenter;
        while(!flag){
            flag = true;
            for(int c = 0; c < clusters.length; c++){
                ArrayList<Point> points = clusters[c].getPoints();
                for (int pointIndex = 0; pointIndex < points.size(); pointIndex++) {
                    Point p = points.get(pointIndex);
                    int minClusterIndex = 0;
                    double minDist = p.distanceToPoint(clusters[minClusterIndex].getCenter());
                    for (int i = 1; i < clusters.length; i++) {
                        if (p.distanceToPoint(clusters[i].getCenter()) < minDist) {
                            minClusterIndex = i;
                            minDist = p.distanceToPoint(clusters[minClusterIndex].getCenter());
                        }
                    }
                    if (minClusterIndex != c) {
                        clusters[minClusterIndex].addPoint(p);
                        clusters[c].remove(p);
                    }
                }
            }

            for (Cluster cluster : clusters) {
                curClusterCenter = cluster.getCenter();
                cluster.reCalculateCenter();
                if (!curClusterCenter.equals(cluster.getCenter())) {
                    flag = false;
                    break;
                }
            }
        }
    }

    private void initializeClusters() {
        for(int r = 0; r < out2.length; r++){
            for (int c = 0; c < out2[0].length; c++){
                if(out2[r][c] == 255){
                    Point p = new Point(r,c);
                    int minClusterIndex = 0;
                    double minDist = p.distanceToPoint(clusters[minClusterIndex].getCenter());
                    for (int i = 1; i < clusters.length; i++) {
                        if(p.distanceToPoint(clusters[i].getCenter()) < minDist){
                            minClusterIndex = i;
                            minDist = p.distanceToPoint(clusters[minClusterIndex].getCenter());
                        }
                    }
                    clusters[minClusterIndex].addPoint(p);
                }
            }
        }
    }

    private void performSecondThreshold(short[][] out2) {
        for (int r = 0; r < out2.length; r++) {
            for (int c = 0; c < out2[0].length; c++) {
                int dist = out2[r][c];
                if (dist > THRESHOLD2) {
                    out2[r][c] = 255;
                } else {
                    out2[r][c] = 0;
                }
            }
        }
    }

    private int sumOf(short[][] kernel) {
        int sum = 0;
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[i].length; j++) {
                sum += kernel[i][j];
            }
        }

        if (sum == 0) return 1;
        return sum;
    }

    private void performBlur(short[][] out, short[][] out2) {
        for (int r = 0; r < out.length - kernel.length - 1; r++) {
            for (int c = 0; c < out[0].length - kernel.length - 1; c++) {

                int outputColor = calculateOutputFrom(r, c, out, kernel);
                out2[r][c] = (short)(outputColor/ kernelWeight);
                if (out2[r][c] < 0) out2[r][c] = 0;
                if (out2[r][c] > 255) out2[r][c] = 255;
            }
        }
    }

    private int calculateOutputFrom(int r, int c, short[][] im, short[][] kernal) {
        int out = 0;
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[i].length; j++) {
                out += im[r+i][c+j]*kernal[i][j];
            }
        }

        return out;
    }

    private void performThreshold(PixelLib.ColorComponents2d img, short[][] out) {
        for (int r = 0; r < out.length; r++) {
            for (int c = 0; c < out[0].length; c++) {
                double dist = distance(red, img.red[r][c], green, img.green[r][c], blue, img.blue[r][c]);
                if (dist > THRESHOLD) {
                    out[r][c] = 0;
                } else {
                    out[r][c] = 255;
                }
            }
        }
    }

    public double distance(short r1, short r2, short g1, short g2, short b1, short b2) {
        int dr = r2-r1;
        int dg = g2-g1;
        int db = b2-b1;

        return Math.sqrt(dr*dr + dg*dg + db*db);
    }
}