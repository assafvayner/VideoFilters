import processing.core.PApplet;

public class MouseCircleFilter implements DrawableFilter {
    private int num = 100;


    @Override
    public void drawingFilter(PApplet window) {
        window.fill(255,0,0,100);
        window.ellipse(window.mouseX, window.mouseY, num,num);
    }
}
