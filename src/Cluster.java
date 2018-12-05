import java.util.ArrayList;

public class Cluster {

    private ArrayList<Point> points;
    private Point center;
    private boolean centerFlag; //true means calculated

    public Point getCenter() {
        if(!centerFlag){
            reCalculateCenter();

        }
        return center;
    }

    private void reCalculateCenter() {
        int xSum = 0, ySum = 0;
        for (Point p: this.points) {
            xSum += p.getX();
            ySum += p.getY();
        }
        xSum/=this.size(); //int division intentional
        ySum/=this.size(); //int division intentional

        this.center = new Point(xSum, ySum);

        centerFlag = true;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point p) {
        this.centerFlag = false;
        this.points.add(p);
    }

    public void addPoints(ArrayList<Point> p) {
        this.centerFlag = false;
        this.points.addAll(p);
    }

    public void clearPoints(){
        this.centerFlag = false;
        this.points = new ArrayList<>();
    }

    public Cluster() {
        this.centerFlag = false;
        this.points = new ArrayList<>();
    }

    public Cluster(ArrayList<Point> points) {
        this.centerFlag = false;
        this.points = points;
    }

    public int size() {
        return this.points.size();
    }

    public String toString(){
        return  "center: " + this.center.toString() + ", " + this.size() + " points";
    }

}
