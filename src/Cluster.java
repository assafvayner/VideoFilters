import java.util.ArrayList;

public class Cluster {

    private ArrayList<Point> points;
    private Point center;

    public Cluster() {
        this.points = new ArrayList<>();
        this.center = new Point((int)(Math.random()*FilterView.getWebcamWidth()),(int)(Math.random()*FilterView.getWebcamHeight()));
    }

    public Cluster(ArrayList<Point> points) {
        this.points = points;
        this.center = new Point((int)(Math.random()*FilterView.getWebcamWidth()),(int)(Math.random()*FilterView.getWebcamHeight()));
    }


    public Point getCenter() {
        return center;
    }

    public void reCalculateCenter() {
        int xSum = 0, ySum = 0;
        for (Point p: this.points) {
            xSum += p.getX();
            ySum += p.getY();
        }
        xSum/= this.size(); //int division intentional
        ySum/= this.size(); //int division intentional

        this.center = new Point(xSum, ySum);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point p) {
        this.points.add(p);
    }

    public void remove(Point p){
        this.points.remove(p);
    }

    public void addPoints(ArrayList<Point> p) {
        this.points.addAll(p);
    }

    public void clearPoints(){
        this.points = new ArrayList<>();
    }



    public int size() {
        if(this.points.size()== 0){
            return 1;
        }
        return this.points.size();
    }

    public String toString(){
        return  "center: " + this.center.toString() + ", " + this.size() + " points";
    }

}
