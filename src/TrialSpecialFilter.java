import processing.core.PApplet;

public class TrialSpecialFilter implements  SpecialFilter {

    @Override
    public int[] specialFilter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents1d pixel = PixelLib.getColorComponents1d(pixels, width, height);
        pixels = PixelLib.combineColorComponents(new short[width * height], pixel.green, pixel.blue, pixel.alpha);
        return pixels;
    }

    @Override
    public void specialDrawingFilter(PApplet window) {
        window.fill(255,0,0,255);
        window.ellipse(window.mouseX, window.mouseY, 100, 100);
    }
}
