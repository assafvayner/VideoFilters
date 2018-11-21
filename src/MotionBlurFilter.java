import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;

public class MotionBlurFilter implements PixelFilter {

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        int rad = Integer.parseInt(JOptionPane.showInputDialog("Blur radius? "));
        short sum = 0, count = 0;
        short[][] img = PixelLib.convertTo2dArray(PixelLib.convertToShortGreyscale(pixels), width, height);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                for (int i = c; i < c + rad || i < width; i++){
                    sum += img[r][i];
                    count++;
                }
                img[r][c] = (short)(sum/count);
                sum = 0;
                count = 0;
            }
        }
        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }
}
