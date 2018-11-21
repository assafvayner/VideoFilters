public class RemoveRedFilter implements PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents1d pixel = PixelLib.getColorComponents1d(pixels, width, height);
        pixels = PixelLib.combineColorComponents(new short[width * height], pixel.green, pixel.blue, pixel.alpha);
        return pixels;
    }
}
