import javax.swing.JOptionPane;

public class PolychromeFilter implements PixelFilter {
    private int num;

    public PolychromeFilter(){
        this.num = Integer.parseInt(JOptionPane.showInputDialog("how many shades? "));
    }

    @Override
    public int[] filter(int[] pixels, int width, int height){
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        int size = 255/num;
        for (int i = 0; i < bwpixels.length; i++) {
            int n = bwpixels[i]/size;
            bwpixels[i] = (short)((2*n+1)*size/2);
        }
        PixelLib.fill1dArray(bwpixels, pixels);
        return pixels;
    }

}