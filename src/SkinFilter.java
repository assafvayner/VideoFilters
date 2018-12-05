import javax.swing.*;
import java.util.ArrayList;



public class SkinFilter implements PixelFilter {

    final short[][] kernel = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};

    private static short[][] out;
    private static short[][] out2;

    private int kernelWeight;

    private short red = 186;
    private short green = 108;
    private short blue = 73;

    private double THRESHOLD = 80;
    private static final int THRESHOLD2 = 254;

    private int numClusters = 2;

    public SkinFilter() {
        //numClusters = Integer.parseInt( JOptionPane.showInputDialog("enter a number"));
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

        Cluster[] clusters = new Cluster[numClusters];
        
        // TODO:  Start your k-means code here

        // as last step, loop over all points in all your clusters
        //   change color values in img depending on what cluster each
        //   point is part of.
        // -----------------------------------------

        PixelLib.fill1dArray(out2, pixels);
        return pixels;
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

    private int sumOf(short[][] kernal) {
        int sum = 0;
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[i].length; j++) {
                sum += kernal[i][j];
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