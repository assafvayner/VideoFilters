public class YellowOnlyFilter implements  PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        PixelLib.ColorComponents1d pixelColors = PixelLib.getColorComponents1d(pixels, width, height);
        for (int i = 0; i < bwpixels.length; i++) {
            pixelColors.red[i] = 255;
            pixelColors.green[i] = 255;
            pixelColors.blue[i] = bwpixels[i];
        }
        return  PixelLib.combineColorComponents(pixelColors);

    }
}
