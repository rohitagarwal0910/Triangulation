public class Triangle implements TriangleInterface {
    Point p1, p2, p3;

    Triangle(Point a, Point b, Point c) {
        p1 = a;
        p2 = b;
        p3 = c;
    }

    @Override
    public PointInterface[] triangle_coord() {
        return new Point[] { p1, p2, p3 };
    }

}