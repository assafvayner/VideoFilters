public class FourWayReflectFilter implements PixelFilter {
    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] img = PixelLib.convertTo2dArray(bwpixels, width, height);

        for(int r = 0; r < height / 2; r++){
            for(int c = width/2; c< width; c++){
                img[r][c] = img[r][width - c];
            }
        }

        for (int r = height / 2; r < height; r++) {
            for (int c = 0; c < width; c++) {
                img[r][c] = img[height - r][c];
            }
        }


        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }
}