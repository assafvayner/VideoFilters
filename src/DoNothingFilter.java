public class DoNothingFilter implements PixelFilter {

    @Override
    public int[] filter(int[] pixels, int width, int height){
        //returns unchanged pixels array
        return pixels;
    }
}
