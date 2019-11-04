public class Edge implements EdgeInterface {
    Point p1, p2;

    Edge(Point a, Point b) {
        p1 = a;
        p2 = b;
    }

    @Override
    public PointInterface[] edgeEndPoints() {
        return new Point[] { p1, p2 };
    }

}