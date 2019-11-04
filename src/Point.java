public class Point implements PointInterface {
    float x, y, z;

    Point(float a, float b, float c) {
        x = a;
        y = b;
        z = c;
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

}