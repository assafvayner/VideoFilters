public class DarkenFilter implements PixelFilter{

    @Override
    public int[] filter(int[] pixels, int width, int height){

        short[] bwPixels = PixelLib.convertToShortGreyscale(pixels);
        for(int i = 0; i < bwPixels.length; i++){
            bwPixels[i] = (short)(bwPixels[i]/2);
        }

        PixelLib.fill1dArray(bwPixels, pixels);
        return pixels;
    }
}
