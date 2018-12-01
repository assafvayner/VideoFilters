public class SobelEdgeDetection implements PixelFilter {
    private short[][] arrx = {{-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}};

    private final kernel Gx = new kernel(arrx);

    private short[][] arry = {{1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}};
    private final kernel Gy = new kernel(arry);

    private short[][] arr1 = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
    private kernel thin1 = new kernel(arr1);

    private short[][] arr2 = {{0, 0, 0}, {1, 1, 0}, {0, 1, 0}};
    private kernel thin2 = new kernel(arr2);

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

                output[r + Gx.getHeight() / 2][c + Gx.getWidth() / 2] = (short) Math.sqrt(valX * valX + valY * valY);
            }
        }


        int sum;
        for (int t = 0; t < 4; t++) {
            for (int r = 0; r < im.length - thin1.getHeight(); r++) {
                for (int c = 0; c < im[0].length - thin1.getWidth(); c++) {
                    sum = 0;
                    for (int i = 0; i < thin1.getHeight(); i++) {
                        for (int j = 0; j < thin1.getHeight(); j++) {
                            sum += output[r + i][c + j] * thin1.getKernel()[i][j];
                        }
                    }
                    im[r + thin1.getHeight() / 2][c + thin1.getWidth() / 2] = (short) (sum / thin1.getWeight());
                }
            }
            for (int r = 0; r < im.length - thin2.getHeight(); r++) {
                for (int c = 0; c < im[0].length - thin2.getWidth(); c++) {
                    sum = 0;
                    for (int i = 0; i < thin2.getHeight(); i++) {
                        for (int j = 0; j < thin2.getHeight(); j++) {
                            sum += im[r + i][c + j] * thin2.getKernel()[i][j];
                        }
                    }
                    output[r + thin2.getHeight() / 2][c + thin2.getWidth() / 2] = (short) (sum / thin2.getWeight());
                }
            }
            thin1.rotate();
            thin2.rotate();
        }
        PixelLib.fill1dArray(output, pixels);
        return pixels;
    }

    private short threshold(short sum) {
        if (sum < 255 / 2) {
            return 0;
        } else {
            return 255;
        }
    }

}
