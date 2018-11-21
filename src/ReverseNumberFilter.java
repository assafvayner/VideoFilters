public class ReverseNumberFilter implements PixelFilter {

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[][] img = PixelLib.convertTo2dArray(PixelLib.convertToShortGreyscale(pixels), width, height);
        for (int row = 0; row < height ; row++) {
            for (int col = 0; col < width; col++) {
                img[row][col] = (short)(255 - img[row][col]);
            }
        }
        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }

}
