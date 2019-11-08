public class Triangle implements TriangleInterface, Comparable<Triangle> {
    Point p1, p2, p3;
    Edge e1, e2, e3;
    LinkedList<Triangle> triangles;
    Component component;
    Triangle connectedTo;
    int sno;
    boolean visited;
    int bfs_no;

    Triangle(Point p1, Point p2, Point p3, Edge e1, Edge e2, Edge e3, int sno) {
        if (p1.compareTo(p2) > 0) {
            Point tp = p2;
            p2 = p1;
            p1 = tp;
        }
        if (p2.compareTo(p3) > 0) {
            Point tp = p3;
            p3 = p2;
            p2 = tp;
        }
        if (p1.compareTo(p2) > 0) {
            Point tp = p2;
            p2 = p1;
            p1 = tp;
        }
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
        this.sno = sno;
        triangles = new LinkedList<Triangle>();
        connectedTo = null;
        component = new Component(sno);
        component.triangles.add(this);
    }

    @Override
    public PointInterface[] triangle_coord() {
        return new Point[] { p1, p2, p3 };
    }

    @Override
    public int compareTo(Triangle o) {
        return (p1.compareTo(o.p1) != 0) ? p1.compareTo(o.p1)
                : (p2.compareTo(o.p2) != 0) ? p2.compareTo(o.p2) : p3.compareTo(o.p3);
    }

    public Triangle isConnectedTo(){
        if (connectedTo == null) return this;
        connectedTo = connectedTo.isConnectedTo();
        return connectedTo;
    }
}