public class SquareRootFilter implements PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents1d pixelColors = PixelLib.getColorComponents1d(pixels, width, height);
        for (int i = 0; i < pixels.length; i++) {
            pixelColors.red[i] = (short)(Math.sqrt(pixelColors.red[i])*Math.sqrt(255));
            pixelColors.green[i] = (short)(Math.sqrt(pixelColors.green[i]*Math.sqrt(255)));
            pixelColors.blue[i] = (short)(Math.sqrt(pixelColors.blue[i]*Math.sqrt(255)));
        }
        return PixelLib.combineColorComponents(pixelColors);
    }
}
