public class PrimarySecondaryColorsFilter implements PixelFilter {
    private final double middleOfSpectrum = 255/2;
    private final short HIGH = 255;
    private final short LOW = 0;

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents1d pixelColors = PixelLib.getColorComponents1d(pixels,width, height);
        for (int i = 0; i < pixels.length; i++) {
            pixelColors.red[i] = setColorLevel(pixelColors.red[i]);
            pixelColors.green[i] = setColorLevel(pixelColors.green[i]);
            pixelColors.blue[i] = setColorLevel(pixelColors.blue[i]);
        }

        return PixelLib.combineColorComponents(pixelColors);
    }

    private short setColorLevel(short val) {
        if(val > middleOfSpectrum){
            return HIGH;
        }
        return LOW;
    }
}
