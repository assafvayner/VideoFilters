public class Point {


    private int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point p){
        return (p.getX() == this.x && p.getY() == this.y);
    }

    public double distanceToPoint(Point p){
        return Math.sqrt(((this.x - p.getX())*(this.x - p.getX())) + ((this.y - p.getY())*(this.y - p.getY())));
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
