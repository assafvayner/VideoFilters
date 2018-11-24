import processing.core.PApplet;

public class TrialSpecialFilter implements  SpecialFilter {
    @Override
    public int[] specialFilter(int[] pixels, int width, int height, PApplet window) {
        window.fill(255, 0, 0, 100);
        window.ellipse(window.width / 2, window.height / 2, 100, 100);
        return pixels;
    }
}
