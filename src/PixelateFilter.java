import javax.swing.*;

public class PixelateFilter implements PixelFilter{
    private static int x;

    public PixelateFilter(){
        x = Integer.parseInt(JOptionPane.showInputDialog("pixel radius"));
    }

    public int[] filter(int[] pixels, int width, int height) {
        short[][] img = PixelLib.convertTo2dArray(PixelLib.convertToShortGreyscale(pixels), width, height);
        for(int r = x; r < height - x; r += x + 1){
            for(int c = x; c < width - x; c += x + 1){
                short a = img[r][c];
                for (int i = r - x; i < r + x; i++) {
                    for (int j = c - x; j < c + x; j++) {
                        img[i][j] = a;
                    }

                }
            }
        }
        PixelLib.fill1dArray(img, pixels);
        return pixels;

    }
}
