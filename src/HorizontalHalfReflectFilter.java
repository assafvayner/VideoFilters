public class HorizontalHalfReflectFilter implements PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] img = PixelLib.convertTo2dArray(bwpixels, width, height);

        for(short[] row : img){
            for(int c = width/2; c< width; c++){
                row[c] = row[width -c];
            }
        }

        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }
}
