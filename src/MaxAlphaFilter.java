public class MaxAlphaFilter implements PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents1d pixelColors = PixelLib.getColorComponents1d(pixels, width, height);
        for (int i = 0; i < pixelColors.alpha.length; i++) {
            pixelColors.alpha[i] = 255;
        }
        return PixelLib.combineColorComponents(pixelColors);
    }
}
