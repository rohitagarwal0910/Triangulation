public class Point implements PointInterface, Comparable<Point> {
    float x, y, z;
    LinkedList<Triangle> triangles = new LinkedList<Triangle>();
    LinkedList<Edge> edges = new LinkedList<Edge>();
    LinkedList<Point> points = new LinkedList<Point>();
    boolean visited;

    Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float[] getXYZcoordinate() {
        return new float[] { x, y, z };
    }

    @Override
    public int compareTo(Point o) {
        if (x > o.x)
            return 1;
        if (x < o.x)
            return -1;
        if (y > o.y)
            return 1;
        if (y < o.y)
            return -1;
        if (z > o.z)
            return 1;
        if (z < o.z)
            return -1;
        return 0;
    }
}