public class ConvolutionFilter implements PixelFilter {
    private final short[][] matrix = {  {-1, -1, -1, -1, -1},
                                        {-1, -1, -1, -1, -1},
                                        {-1, -1, 24, -1, -1},
                                        {-1, -1, -1, -1, -1},
                                        {-1, -1, -1, -1, -1}};
    private short matrixSum;

    public ConvolutionFilter(){
        short sum = 0;
        for (short[] row : matrix) {
            for (int col = 0; col < matrix[0].length; col++) {
                sum += row[col];
            }
        }
        if(sum == 0) {
            matrixSum = 1;
        }else{
            matrixSum = sum;
        }
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        short[][] im = PixelLib.convertTo2dArray(PixelLib.convertToShortGreyscale(pixels), width, height);
        short[][] output = new short[height][width];
        short sum;

        for (int r = 0; r < im.length - matrix.length; r++) {
            for (int c = 0; c < im[0].length - matrix[0].length; c++) {
                sum = 0;

                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        sum += im[r + i][c + j] * matrix[i][j];
                    }
                }

                sum /= matrixSum;

                if(sum < 0){
                    output[r + matrix.length/2][c + matrix[0].length/2] = 0;
                }else if(sum > 255){
                    output[r + matrix.length/2][c + matrix[0].length/2] = 255;
                }else{
                    output[r + matrix.length/2][c + matrix[0].length/2] = sum;
                }
            }
        }

        PixelLib.fill1dArray(output, pixels);
        return pixels;
    }
}
