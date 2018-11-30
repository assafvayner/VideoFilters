public class kernel {
    public short[][] getKernel() {
        return kernel;
    }

    private short[][] kernel;
    private short height, width, weight;


    public short getHeight() {
        return height;
    }

    public short getWidth() {
        return width;
    }

    public short getWeight() {
        return weight;
    }

    public kernel(short[][] kernel) {
        this.kernel = kernel;
        height = (short)kernel.length;
        width = (short)kernel[0].length;
        weight = sum(kernel);
    }

    private short sum(short[][] kernel) {
        short sum = 0;
        for (short[] row : kernel) {
            for (int col = 0; col < width; col++) {
                sum += row[col];
            }
        }
        if(sum == 0) {
            return 1;
        }

        return sum;
    }
}
