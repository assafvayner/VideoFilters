import processing.core.PApplet;

public interface SpecialFilter {

    public int[] specialFilter(int[] pixels, int width, int height);

    public void specialDrawingFilter(PApplet window);

}
