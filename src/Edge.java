public class Edge implements EdgeInterface, Comparable<Edge> {
    Point p1, p2;
    double length;
    LinkedList<Triangle> triangles;

    Edge(Point p1, Point p2) {
        if (p1.compareTo(p2) > 0) {
            Point tp = p2;
            p2 = p1;
            p1 = tp;
        }
        this.p1 = p1;
        this.p2 = p2;
        this.length = Math.sqrt(Math.pow((double) p1.x - p2.x, 2) + Math.pow((double) p1.y - p2.y, 2)
                + Math.pow((double) p1.z - p2.z, 2));
        // this.length = ((this.p1.x - this.p2.x) * (this.p1.x - this.p2.x)
        // + (this.p1.y - this.p2.y) * (this.p1.y - this.p2.y)
        // + (this.p1.z - this.p2.z) * (this.p1.z - this.p2.z));
        triangles = new LinkedList<Triangle>();
    }

    @Override
    public PointInterface[] edgeEndPoints() {
        return new Point[] { p1, p2 };
    }

    @Override
    public int compareTo(Edge o) {
        return (p1.compareTo(o.p1) != 0) ? p1.compareTo(o.p1) : p2.compareTo(o.p2);
    }
}