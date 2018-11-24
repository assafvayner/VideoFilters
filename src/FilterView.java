import processing.core.PApplet;
import processing.core.PImage;
import processing.video.Capture;

import javax.swing.*;
import java.util.Arrays;

/**
 * Side-by-side webcam view for image filtering
 * by David Dobervich
 */
public class FilterView extends PApplet {
    private static final int WEBCAM_WIDTH = 640;
    private static final int WEBCAM_HEIGHT = 480;

    private Capture video;
    private PImage frame, filteredFrame, oldFilteredFrame;
    private boolean loading = false;

    private PixelFilter filter;
    private DrawableFilter drawingLayer;
    private SpecialFilter specialFilter;

    public void settings() {
        size(WEBCAM_WIDTH * 2, WEBCAM_HEIGHT);
    }

    public void setup() {
        // This the default video input
        video = new Capture(this, WEBCAM_WIDTH, WEBCAM_HEIGHT);

        // Start capturing the images from the camera
        video.start();

        frameRate(5);
    }

    public void draw() {
        if (frame != null && filteredFrame != null) {
            if (!loading) {
                image(frame, 0, 0);
                image(filteredFrame, WEBCAM_WIDTH, 0);

                if (drawingLayer != null) {
                    drawingLayer.drawingFilter(this);
                }
            } else if (oldFilteredFrame != null){
                image(frame, 0, 0);
                image(oldFilteredFrame, WEBCAM_WIDTH, 0);

                if (drawingLayer != null) {
                    drawingLayer.drawingFilter(this);
                }
            }
        }
    }

    public void captureEvent(Capture c) {
        loading = true;
        oldFilteredFrame = filteredFrame;

        c.read();
        frame = c.get();
        filteredFrame = makeFrameCopy(frame);
        filteredFrame.pixels = runFilters(filteredFrame);
        filteredFrame.updatePixels();

        loading = false;
    }

    private PImage makeFrameCopy(PImage frame) {
        frame.loadPixels();
        int[] framePixels = frame.pixels;
        int[] updatedFramePixels = Arrays.copyOf(framePixels, framePixels.length);
        PImage outputImage = new PImage(frame.width, frame.height);
        outputImage.loadPixels();
        outputImage.pixels = updatedFramePixels;
        return outputImage;
    }

    private int[] runFilters(PImage frameToFilter) {
        if (specialFilter != null){
            return specialFilter.specialFilter(frameToFilter.pixels, frameToFilter.width, frameToFilter.height, this);
        }else if (filter != null){
            return filter.filter(frameToFilter.pixels, frameToFilter.width, frameToFilter.height);
        }
        return pixels;
    }

    public void keyReleased() {
        if (key == 'f') {
            this.filter = loadNewFilter();
            this.specialFilter = null;
        }

        if (key == 'd') {
            this.drawingLayer = loadNewDrawingFilter();
        }

        if(key == 's'){
            this.specialFilter = loadNewSpecialFilter();
        }
    }

    private SpecialFilter loadNewSpecialFilter() {
        String name = JOptionPane.showInputDialog("Type the name of your special specialFilter class");
        SpecialFilter f = null;
        try {
            Class c = Class.forName(name);
            f = (SpecialFilter) c.newInstance();
        } catch (Exception e) {
            System.err.println("Something went wrong when loading your specialFilter! " + e.getMessage());
        }

        return f;
    }

    private DrawableFilter loadNewDrawingFilter() {
        String name = JOptionPane.showInputDialog("Type the name of your drawable specialFilter class");
        DrawableFilter f = null;
        try {
            Class c = Class.forName(name);
            f = (DrawableFilter) c.newInstance();
        } catch (Exception e) {
            System.err.println("Something went wrong when loading your specialFilter! " + e.getMessage());
        }
        return f;
    }

    private PixelFilter loadNewFilter() {
        String name = JOptionPane.showInputDialog("Type the name of your specialFilter class");
        PixelFilter f = null;
        try {
            Class c = Class.forName(name);
            f = (PixelFilter)c.newInstance();
        } catch (Exception e) {
            System.err.println("Something went wrong when loading your specialFilter! " + e.getMessage());
        }

        return f;
    }

    public static void main(String[] args) {
        PApplet.main("FilterView", args);
    }
}

