public class AverageFilter implements PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents1d pixelColors = PixelLib.getColorComponents1d(pixels,width, height);
        for (int i = 0; i < pixels.length; i++) {
            short ave = averageColor(pixelColors.red[i], pixelColors.green[i], pixelColors.blue[i]);
            pixelColors.red[i] = ave;
            pixelColors.green[i] = ave;
            pixelColors.blue[i] = ave;
        }

        return PixelLib.combineColorComponents(pixelColors);
    }

    private short averageColor(short r, short g, short b) {
        return (short)((r+g+b)/3);
    }
}
