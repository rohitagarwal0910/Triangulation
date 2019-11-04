public class Shape implements ShapeInterface {

    @Override
    public boolean ADD_TRIANGLE(float[] triangle_coord) {
        return false;
    }

    @Override
    public int TYPE_MESH() {
        return 0;
    }

    @Override
    public EdgeInterface[] BOUNDARY_EDGES() {
        return null;
    }

    @Override
    public int COUNT_CONNECTED_COMPONENTS() {
        return 0;
    }

    @Override
    public TriangleInterface[] NEIGHBORS_OF_TRIANGLE(float[] triangle_coord) {
        return null;
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        return null;
    }

    @Override
    public PointInterface[] VERTEX_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        return null;
    }

    @Override
    public TriangleInterface[] EXTENDED_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        return null;
    }

    @Override
    public TriangleInterface[] INCIDENT_TRIANGLES(float[] point_coordinates) {
        return null;
    }

    @Override
    public PointInterface[] NEIGHBORS_OF_POINT(float[] point_coordinates) {
        return null;
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
        return null;
    }

    @Override
    public TriangleInterface[] FACE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
        return null;
    }

    @Override
    public boolean IS_CONNECTED(float[] triangle_coord_1, float[] triangle_coord_2) {
        return false;
    }

    @Override
    public TriangleInterface[] TRIANGLE_NEIGHBOR_OF_EDGE(float[] edge_coordinates) {
        return null;
    }

    @Override
    public int MAXIMUM_DIAMETER() {
        return 0;
    }

    @Override
    public PointInterface[] CENTROID() {
        return null;
    }

    @Override
    public PointInterface CENTROID_OF_COMPONENT(float[] point_coordinates) {
        return null;
    }

    @Override
    public PointInterface[] CLOSEST_COMPONENTS() {
        return null;
    }

}