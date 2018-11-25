import jdk.nashorn.internal.scripts.JO;
import processing.core.PApplet;

import javax.swing.*;

public class MouseRevealFilter implements SpecialFilter {
    private boolean[][] seen = new boolean[FilterView.getWebcamHeight()][2 * FilterView.getWebcamWidth()];
    private final int BLACK = 0;
    private int delta;

    public MouseRevealFilter() {
        delta = Integer.parseInt(JOptionPane.showInputDialog("radius: "));
    }

    @Override
    public int[] specialFilter(int[] pixels, int width, int height) {
        int[][] img = PixelLib.convertTo2dArray(pixels, width, height);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (!seen[row][FilterView.getWebcamWidth() + column]) {
                    img[row][column] = BLACK;
                }
            }
        }
        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }

    @Override
    public void specialDrawingFilter(PApplet window) {
        if (window.mousePressed) {
            try {
                for (int i = window.mouseY - delta; i < window.mouseY + delta; i++) {
                    for (int j = window.mouseX - delta; j < window.mouseX + delta; j++) {
                        seen[i][j] = true;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }


        if (window.keyPressed) {
            seen = new boolean[FilterView.getWebcamHeight()][2 * FilterView.getWebcamWidth()];
        }
    }
}
