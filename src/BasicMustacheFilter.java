import processing.core.PApplet;

public class BasicMustacheFilter implements SpecialFilter {

    private int xpos = 0, ypos = 0;
    private final int R = 154, G = 89, B = 90;
    private final int threshold = 25;
    //private int count = 0, xSum = 0, ySum = 0;


    @Override
    public int[] specialFilter(int[] pixels, int width, int height) {
        int count = 0, xSum = 0, ySum = 0;
        PixelLib.ColorComponents2d pixel = PixelLib.getColorComponents2d(pixels, width, height);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                double dist = Math.sqrt((R - pixel.red[row][column]) * (R - pixel.red[row][column]) +
                        (G - pixel.green[row][column]) * (G - pixel.green[row][column]) +
                        (B - pixel.blue[row][column]) * (B - pixel.blue[row][column]));
                if (dist > threshold) {
                    xSum += column;
                    ySum += row;
                    count++;
                }
            }
        }
        if(count > 0){
            xpos = xSum/count;
            ypos = ySum/count;
        }
        return pixels;
    }

        @Override
        public void specialDrawingFilter (PApplet window) {
            window.fill(0, 0, 0, 255);
            if (xpos != 0 && ypos != 0) {
                window.rect(xpos + FilterView.getWebcamWidth(), ypos, 100, 25);
                xpos = 0;
                ypos = 0;
            }else{
                window.clear();
            }
        }
    }
