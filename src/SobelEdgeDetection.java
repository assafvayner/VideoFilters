public class SobelEdgeDetection implements PixelFilter {
   private short[][] arrx = {  {-1, 0, 1},
           {-2, 0, 2},
           {-1, 0, 1}};

    private final kernel Gx = new kernel(arrx);

    private short[][] arry = {  {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}};
    private final kernel Gy = new kernel(arry);

    public SobelEdgeDetection(){

    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[][] im = PixelLib.convertTo2dArray(PixelLib.convertToShortGreyscale(pixels), width, height);
        short[][] output = new short[height][width];
        short sumX, sumY, valX, valY;

        for (int r = 0; r < im.length - Gx.getHeight(); r++) {
            for (int c = 0; c < im[0].length - Gx.getWidth(); c++) {
                sumX = 0;
                sumY = 0;
                for (int i = 0; i < Gx.getHeight(); i++) {
                    for (int j = 0; j < Gx.getWidth(); j++) {
                        sumX += im[r + i][c + j] * Gx.getKernel()[i][j];
                        sumY += im[r + i][c + j] * Gy.getKernel()[i][j];
                    }
                }

                sumX /= Gx.getWeight();
                sumY /= Gy.getWeight();

                valX = threshold(sumX);
                valY = threshold(sumY);

                output[r + Gx.getHeight()/2][c + Gx.getWidth()/2] = (short)Math.sqrt(valX*valX + valY*valY);
            }
        }

        PixelLib.fill1dArray(output, pixels);
        return pixels;
    }

    private short threshold(short sum) {
        if(sum < 255/2){
            return 0;
        }else {
            return  255;
        }
    }

}
