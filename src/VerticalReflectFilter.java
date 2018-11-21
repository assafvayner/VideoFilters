public class VerticalReflectFilter implements PixelFilter{

    @Override
    public int[] filter(int[] pixels, int width, int height){
        short[] bwPixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] img = PixelLib.convertTo2dArray(bwPixels, width, height);

        int lastRowIndex = img.length - 1;
        for (int r = 0; r < img.length / 2; r++) {
            for (int c = 0; c < img[0].length; c++) {
                short temp = img[r][c];
                img[r][c] = img[lastRowIndex - r][c];
                img[lastRowIndex - r][c] = temp;
            }
        }

        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }
}
