import javax.swing.*;

public class ColorThresholdFilter implements PixelFilter {
    private int R,G,B,threshold;
    private final int BLACK = 0;
    private final int WHITE = Integer.MAX_VALUE;

    public ColorThresholdFilter(){
        R = Integer.parseInt(JOptionPane.showInputDialog("How much red? "));
        G = Integer.parseInt(JOptionPane.showInputDialog("How much green? "));
        B = Integer.parseInt(JOptionPane.showInputDialog("How much blue? "));
        threshold = Integer.parseInt(JOptionPane.showInputDialog("Threshold? "));
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents1d pixel = PixelLib.getColorComponents1d(pixels, width, height);
        for (int i = 0; i < pixels.length; i++) {
            double dist = Math.sqrt((R - pixel.red[i])*(R - pixel.red[i]) +
                    (G - pixel.green[i])*(G - pixel.green[i]) +
                    (B - pixel.blue[i])*(B - pixel.blue[i]));
            if (dist > threshold){
                pixels[i] = BLACK;
            }else{
                pixels[i] = WHITE;
            }
        }

        return pixels;
    }
}
