import javax.swing.JOptionPane;

public class MonochromeFilter implements PixelFilter{
    private int split;

    public MonochromeFilter(){
        split = Integer.parseInt(JOptionPane.showInputDialog("split: "));
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        for (int i = 0; i < bwpixels.length; i++) {
            if(bwpixels[i] > split){
                bwpixels[i] = 255;
            }else{
                bwpixels[i] = 0;
            }
        }
        PixelLib.fill1dArray(bwpixels, pixels);
        return pixels;
    }
}
