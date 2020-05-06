public class Shape implements ShapeInterface {
    int triangle_sno = 0;
    // int no_of_components = 0;
    RBTree<Triangle, Triangle> triangles = new RBTree<Triangle, Triangle>();
    RBTree<Point, Point> points = new RBTree<Point, Point>();
    RBTree<Edge, Edge> edges = new RBTree<Edge, Edge>();
    LinkedList<Component> components = new LinkedList<Component>();

    boolean checkCollinear(Point p1, Point p2, Point p3, double len1, double len2) {
        if (len1 == 0 || len2 == 0)
            return true;
        float x1 = p1.x - p2.x;
        float x2 = p3.x - p2.x;
        float y1 = p1.y - p2.y;
        float y2 = p3.y - p2.y;
        float z1 = p1.z - p2.z;
        float z2 = p3.z - p2.z;
        double cos = (x1 * x2 + y1 * y2 + z1 * z2) / (len1 * len2);
        if (Math.abs(cos) >= 1) {
            return true;
        }
        if (Math.abs(cos) < 0.5) {
            return false;
        }
        double tan = Math.tan(Math.acos(cos));
        return Math.abs(tan) < 0.001;
    }

    @Override
    public boolean ADD_TRIANGLE(float[] triangle_coord) {
        Point tp1, tp2, tp3;
        Point p1, p2, p3;
        Edge e1, e2, e3;
        Edge te1, te2, te3;
        // temp points and edges
        tp1 = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
        tp2 = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
        tp3 = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);

        // if (tp1.compareTo(tp2) > 0) {
        // Point tp = tp2;
        // tp2 = tp1;
        // tp1 = tp;
        // }
        // if (tp2.compareTo(tp3) > 0) {
        // Point tp = tp3;
        // tp3 = tp2;
        // tp2 = tp;
        // }
        // if (tp1.compareTo(tp2) > 0) {
        // Point tp = tp2;
        // tp2 = tp1;
        // tp1 = tp;
        // }

        te1 = new Edge(tp1, tp2);
        te2 = new Edge(tp2, tp3);
        te3 = new Edge(tp1, tp3);

        // checking triangle validity
        // if ((p3.getX() - p2.getX()) / (p2.getX() - p1.getX()) == (p3.getY() -
        // p2.getY()) / (p2.getY() - p1.getY())) // by slope
        // if ((te1.length + te2.length) <= te3.length || (te2.length + te3.length) <=
        // te1.length
        // || (te3.length + te1.length) <= te2.length)
        // return false;
        if (checkCollinear(tp1, tp2, tp3, te1.length, te2.length)
                || checkCollinear(tp2, tp3, tp1, te2.length, te3.length)
                || checkCollinear(tp3, tp1, tp2, te3.length, te1.length)) {
            return false;
        }

        // boolean bp1 = false, bp2 = false, bp3 = false;

        // search for existing points and edges and adding to rbtree
        boolean be1 = false, be2 = false, be3 = false;
        RedBlackNode<Point, Point> n;
        n = points.search(tp1);
        if (n != null)
            p1 = n.value;
        else {
            p1 = tp1;
            // bp1 = true;
            points.insert(p1, p1);
        }
        n = points.search(tp2);
        if (n != null)
            p2 = n.value;
        else {
            p2 = tp2;
            // bp2 = true;
            points.insert(p2, p2);
        }
        n = points.search(tp3);
        if (n != null)
            p3 = n.value;
        else {
            p3 = tp3;
            // bp3 = true;
            points.insert(p3, p3);
        }

        RedBlackNode<Edge, Edge> m;
        m = edges.search(te1);
        if (m != null) {
            e1 = m.value;
        } else {
            e1 = te1;
            be1 = true;
            edges.insert(e1, e1);
        }
        m = edges.search(te2);
        if (m != null) {
            e2 = m.value;
        } else {
            e2 = te2;
            be2 = true;
            edges.insert(e2, e2);
        }
        m = edges.search(te3);
        if (m != null)
            e3 = m.value;
        else {
            e3 = te3;
            be3 = true;
            edges.insert(e3, e3);
        }

        // making triangle
        Triangle t = new Triangle(p1, p2, p3, e1, e2, e3, triangle_sno++);
        // no_of_components++;
        components.add(t.component);

        // adding triangle neighbours
        ListNode<Triangle> ttn;
        ttn = e1.triangles.head;
        if (ttn != null) {
            merge(t, ttn.value);
        }
        while (ttn != null) {
            Triangle tt = ttn.value;
            t.triangles.endAdd(tt);
            tt.triangles.endAdd(t);
            ttn = ttn.next;
        }
        ttn = e2.triangles.head;
        if (ttn != null) {
            merge(t, ttn.value);
        }
        while (ttn != null) {
            Triangle tt = ttn.value;
            t.triangles.endAdd(tt);
            tt.triangles.endAdd(t);
            ttn = ttn.next;
        }
        ttn = e3.triangles.head;
        if (ttn != null) {
            merge(t, ttn.value);
        }
        while (ttn != null) {
            Triangle tt = ttn.value;
            t.triangles.endAdd(tt);
            tt.triangles.endAdd(t);
            ttn = ttn.next;
        }

        // adding triangle to rbtree
        triangles.insert(t, t);

        // adding triangle to points
        p1.triangles.endAdd(t);
        p2.triangles.endAdd(t);
        p3.triangles.endAdd(t);

        // adding edges to points
        // if(be1) p1.edges.add(e1);
        // if(be3) p1.edges.add(e3);
        // if(be1) p2.edges.add(e1);
        // if(be2) p2.edges.add(e2);
        // if(be3) p3.edges.add(e3);
        // if(be2) p3.edges.add(e2);

        // adding points to points
        // p1.points.add(p2);
        // p1.points.add(p3);
        // p2.points.add(p3);
        // p2.points.add(p1);
        // p3.points.add(p1);
        // p3.points.add(p2);

        if (be1) {
            p1.edges.endAdd(e1);
            p2.edges.endAdd(e1);
            p1.points.endAdd(p2);
            p2.points.endAdd(p1);
        }
        if (be2) {
            p2.edges.endAdd(e2);
            p3.edges.endAdd(e2);
            p2.points.endAdd(p3);
            p3.points.endAdd(p2);
        }
        if (be3) {
            p3.edges.endAdd(e3);
            p1.edges.endAdd(e3);
            p3.points.endAdd(p1);
            p1.points.endAdd(p3);
        }

        // Adding trinagle to edges
        e1.triangles.endAdd(t);
        e2.triangles.endAdd(t);
        e3.triangles.endAdd(t);

        return true;
    }

    void merge(Triangle t1, Triangle t2) {
        Triangle small, large;
        t1 = t1.isConnectedTo();
        t2 = t2.isConnectedTo();
        if (t1.component.triangles.size <= t2.component.triangles.size) {
            small = t1;
            large = t2;
        } else {
            small = t2;
            large = t1;
        }
        if (small.component.id != large.component.id) {
            components.remove(small.component);
            // System.out.println(small.connectedTo == large);
            // large = large;
            // small = small;
            // small.component.idObject = large.component.idObject;
            large.component.triangles.tail.next = small.component.triangles.head;
            large.component.triangles.tail = small.component.triangles.tail;
            large.component.triangles.size += small.component.triangles.size;
            small.connectedTo = large;
            // small.component.triangles.head = large.component.triangles.head;
            // if (small.component.idObject.id != large.component.idObject.id) {
            // no_of_components--;
            // ListNode<Triangle> tln = small.component.triangles.head;
            // while (tln != null) {
            // tln.value.component = large.component;
            // tln = tln.next;
            // }
            // }
            // small.component = large.component;
        }
    }

    @Override
    public int TYPE_MESH() {
        RedBlackNode<Edge, Edge> cn = edges.root;
        LinkedList<RedBlackNode<Edge, Edge>> stack = new LinkedList<RedBlackNode<Edge, Edge>>();
        stack.add(cn);
        int ans = 0;
        while (stack.size > 0) {
            RedBlackNode<Edge, Edge> en = stack.remove();
            int es = en.value.triangles.size;
            if (es == 2 && ans == 0)
                ans = 2;
            if (es == 1)
                ans = 1;
            if (es > 2) {
                return 3;
            }
            if (en.right.key != null) {
                stack.add(en.right);
            }
            if (en.left.key != null) {
                stack.add(en.left);
            }
        }
        return (ans == 2) ? 1 : 2;
    }

    @Override
    public EdgeInterface[] BOUNDARY_EDGES() {
        LinkedList<Edge> el = new LinkedList<Edge>();
        RedBlackNode<Edge, Edge> cn = edges.root;
        LinkedList<RedBlackNode<Edge, Edge>> stack = new LinkedList<RedBlackNode<Edge, Edge>>();
        stack.add(cn);
        while (stack.size > 0) {
            RedBlackNode<Edge, Edge> en = stack.remove();
            Edge e = en.value;
            if (e.triangles.size == 1)
                el.add(e);
            if (en.right.key != null) {
                stack.add(en.right);
            }
            if (en.left.key != null) {
                stack.add(en.left);
            }
        }
        Edge[] tr = new Edge[el.size];
        int i = 0;
        ListNode<Edge> te = el.head;
        while (te != null) {
            tr[i++] = te.value;
            te = te.next;
        }
        mergeSortEdge(tr, 0, tr.length-1);
        return (tr.length == 0) ? null : tr;
    }

    @Override
    public int COUNT_CONNECTED_COMPONENTS() {
        return components.size;
    }

    @Override
    public TriangleInterface[] NEIGHBORS_OF_TRIANGLE(float[] triangle_coord) {
        Point tp1, tp2, tp3;
        Edge te1, te2, te3;
        tp1 = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
        tp2 = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
        tp3 = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
        te1 = new Edge(tp1, tp2);
        te2 = new Edge(tp2, tp3);
        te3 = new Edge(tp3, tp1);
        Triangle t = new Triangle(tp1, tp2, tp3, te1, te2, te3, triangle_sno++);
        RedBlackNode<Triangle, Triangle> tn = triangles.search(t);
        if (tn == null)
            return null;
        t = tn.value;
        Triangle[] tr = new Triangle[t.triangles.size];
        ListNode<Triangle> tln;
        tln = t.triangles.head;
        int i = 0;
        while (tln != null) {
            tr[i++] = tln.value;
            tln = tln.next;
        }
        // IPLEMENT SORTING
        // int a = 0, b = 0, c = 0, i = 0;
        // ListNode<Triangle> tn1, tn2, tn3;
        // Triangle t1, t2, t3;
        // tn1 = t.e1.triangles.head;
        // tn2 = t.e2.triangles.head;
        // tn3 = t.e3.triangles.head;
        // while (i < tr.length) {
        // if (tn1 != null) t1
        // }
        mergeSortTriangle(tr, 0, tr.length-1);
        return (tr.length == 0) ? null : tr;
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        Point tp1, tp2, tp3;
        Edge te1, te2, te3;
        tp1 = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
        tp2 = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
        tp3 = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
        te1 = new Edge(tp1, tp2);
        te2 = new Edge(tp2, tp3);
        te3 = new Edge(tp3, tp1);
        Triangle t = new Triangle(tp1, tp2, tp3, te1, te2, te3, triangle_sno++);
        RedBlackNode<Triangle, Triangle> tn = triangles.search(t);
        if (tn == null)
            return null;
        t = tn.value;
        Edge[] tr = new Edge[3];
        tr[0] = t.e1;
        tr[1] = t.e2;
        tr[2] = t.e3;
        return (tr.length == 0) ? null : tr;
    }

    @Override
    public PointInterface[] VERTEX_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        Point tp1, tp2, tp3;
        Edge te1, te2, te3;
        tp1 = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
        tp2 = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
        tp3 = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
        te1 = new Edge(tp1, tp2);
        te2 = new Edge(tp2, tp3);
        te3 = new Edge(tp3, tp1);
        Triangle t = new Triangle(tp1, tp2, tp3, te1, te2, te3, triangle_sno++);
        RedBlackNode<Triangle, Triangle> tn = triangles.search(t);
        if (tn == null)
            return null;
        t = tn.value;
        Point[] tr = new Point[3];
        tr[0] = t.p1;
        tr[1] = t.p2;
        tr[2] = t.p3;
        return (tr.length == 0) ? null : tr;
    }

    @Override
    public TriangleInterface[] EXTENDED_NEIGHBOR_TRIANGLE(float[] triangle_coord) {
        Point tp1, tp2, tp3;
        Edge te1, te2, te3;
        tp1 = new Point(triangle_coord[0], triangle_coord[1], triangle_coord[2]);
        tp2 = new Point(triangle_coord[3], triangle_coord[4], triangle_coord[5]);
        tp3 = new Point(triangle_coord[6], triangle_coord[7], triangle_coord[8]);
        te1 = new Edge(tp1, tp2);
        te2 = new Edge(tp2, tp3);
        te3 = new Edge(tp3, tp1);
        Triangle t = new Triangle(tp1, tp2, tp3, te1, te2, te3, triangle_sno++);
        RedBlackNode<Triangle, Triangle> tn = triangles.search(t);
        if (tn == null)
            return null;
        t = tn.value;
        RBTree<Triangle, Triangle> t_triangles = new RBTree<Triangle, Triangle>();
        // Triangle[] tr = new Triangle[t.p1.triangles.size + t.p2.triangles.size +
        // t.p3.triangles.size];
        ListNode<Triangle> tln;
        tln = t.p1.triangles.head;
        while (tln != null) {
            if (tln.value != t) {
                t_triangles.insert(tln.value, tln.value);
            }
            tln = tln.next;
        }
        tln = t.p2.triangles.head;
        while (tln != null) {
            if (tln.value != t) {
                t_triangles.insert(tln.value, tln.value);
            }
            tln = tln.next;
        }
        tln = t.p3.triangles.head;
        while (tln != null) {
            if (tln.value != t) {
                t_triangles.insert(tln.value, tln.value);
            }
            tln = tln.next;
        }
        Triangle[] tr = new Triangle[t_triangles.size];
        RedBlackNode<Triangle, Triangle> cn = t_triangles.root;
        LinkedList<RedBlackNode<Triangle, Triangle>> stack = new LinkedList<RedBlackNode<Triangle, Triangle>>();
        stack.add(cn);
        int i = 0;
        while (stack.size > 0) {
            RedBlackNode<Triangle, Triangle> en = stack.remove();
            tr[i++] = en.value;
            if (en.right.key != null) {
                stack.add(en.right);
            }
            if (en.left.key != null) {
                stack.add(en.left);
            }
        }
        mergeSortTriangle(tr, 0, tr.length-1);
        return (tr.length == 0) ? null : tr;
    }

    @Override
    public TriangleInterface[] INCIDENT_TRIANGLES(float[] point_coordinates) {
        Point p = new Point(point_coordinates[0], point_coordinates[1], point_coordinates[2]);
        RedBlackNode<Point, Point> pn = points.search(p);
        if (pn == null)
            return null;
        p = pn.value;
        Triangle[] tr = new Triangle[p.triangles.size];
        int i = 0;
        ListNode<Triangle> tn = p.triangles.head;
        while (tn != null) {
            tr[i++] = tn.value;
            tn = tn.next;
        }
        // mergeSortTriangle(tr, 0, tr.length-1);
        return (tr.length == 0) ? null : tr;
    }

    @Override
    public PointInterface[] NEIGHBORS_OF_POINT(float[] point_coordinates) {
        Point p = new Point(point_coordinates[0], point_coordinates[1], point_coordinates[2]);
        RedBlackNode<Point, Point> pn = points.search(p);
        if (pn == null)
            return null;
        p = pn.value;
        Point[] tr = new Point[p.points.size];
        int i = 0;
        ListNode<Point> tn = p.points.head;
        while (tn != null) {
            tr[i++] = tn.value;
            tn = tn.next;
        }
        return (tr.length == 0) ? null : tr;
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
        Point p = new Point(point_coordinates[0], point_coordinates[1], point_coordinates[2]);
        RedBlackNode<Point, Point> pn = points.search(p);
        if (pn == null)
            return null;
        p = pn.value;
        Edge[] tr = new Edge[p.edges.size];
        int i = 0;
        ListNode<Edge> tn = p.edges.head;
        while (tn != null) {
            tr[i++] = tn.value;
            tn = tn.next;
        }
        return (tr.length == 0) ? null : tr;
    }

    @Override
    public TriangleInterface[] FACE_NEIGHBORS_OF_POINT(float[] point_coordinates) {
        return INCIDENT_TRIANGLES(point_coordinates);
    }

    @Override
    public boolean IS_CONNECTED(float[] triangle_coord_1, float[] triangle_coord_2) {
        Point tp1, tp2, tp3;
        Edge te1, te2, te3;
        Triangle t;
        RedBlackNode<Triangle, Triangle> tn;
        tp1 = new Point(triangle_coord_1[0], triangle_coord_1[1], triangle_coord_1[2]);
        tp2 = new Point(triangle_coord_1[3], triangle_coord_1[4], triangle_coord_1[5]);
        tp3 = new Point(triangle_coord_1[6], triangle_coord_1[7], triangle_coord_1[8]);
        te1 = new Edge(tp1, tp2);
        te2 = new Edge(tp2, tp3);
        te3 = new Edge(tp3, tp1);
        t = new Triangle(tp1, tp2, tp3, te1, te2, te3, triangle_sno++);
        tn = triangles.search(t);
        if (tn == null)
            return false;
        Triangle t1 = tn.value;
        tp1 = new Point(triangle_coord_2[0], triangle_coord_2[1], triangle_coord_2[2]);
        tp2 = new Point(triangle_coord_2[3], triangle_coord_2[4], triangle_coord_2[5]);
        tp3 = new Point(triangle_coord_2[6], triangle_coord_2[7], triangle_coord_2[8]);
        te1 = new Edge(tp1, tp2);
        te2 = new Edge(tp2, tp3);
        te3 = new Edge(tp3, tp1);
        t = new Triangle(tp1, tp2, tp3, te1, te2, te3, triangle_sno++);
        tn = triangles.search(t);
        if (tn == null)
            return false;
        Triangle t2 = tn.value;
        return t1.isConnectedTo().component.id == t2.isConnectedTo().component.id;
    }

    @Override
    public TriangleInterface[] TRIANGLE_NEIGHBOR_OF_EDGE(float[] edge_coordinates) {
        Point p1, p2;
        p1 = new Point(edge_coordinates[0], edge_coordinates[1], edge_coordinates[2]);
        p2 = new Point(edge_coordinates[3], edge_coordinates[4], edge_coordinates[5]);
        Edge e = new Edge(p1, p2);
        RedBlackNode<Edge, Edge> en = edges.search(e);
        if (en == null)
            return null;
        e = en.value;
        Triangle[] tr = new Triangle[e.triangles.size];
        int i = 0;
        ListNode<Triangle> tn = e.triangles.head;
        while (tn != null) {
            tr[i++] = tn.value;
            tn = tn.next;
        }
        // mergeSortTriangle(tr, 0, tr.length-1);
        return (tr.length == 0) ? null : tr;
    }

    @Override
    public int MAXIMUM_DIAMETER() {
        ListNode<Component> cn = components.head;
        Component c = cn.value;
        int max = 0;
        while (cn != null) {
            if (cn.value.triangles.size > max) {
                c = cn.value;
                max = c.triangles.size;
            }
            cn = cn.next;
        }
        if (max == 1)
            return 0;
        int maxm = 0;
        ListNode<Triangle> tn = c.triangles.head;
        while (tn != null) {
            ListNode<Triangle> tt = c.triangles.head;
            while (tt != null) {
                tt.value.visited = false;
                tt.value.bfs_no = 0;
                tt = tt.next;
            }
            LinkedList<Triangle> queue = new LinkedList<Triangle>();
            tn.value.visited = true;
            queue.endAdd(tn.value);
            while (queue.size > 0) {
                Triangle t = queue.remove();
                ListNode<Triangle> nbr = t.triangles.head;
                int i = t.bfs_no;
                while (nbr != null) {
                    if (nbr.value.visited == false) {
                        nbr.value.visited = true;
                        nbr.value.bfs_no = i + 1;
                        if (i + 1 > maxm) {
                            maxm = i + 1;
                        }
                        queue.endAdd(nbr.value);
                    }
                    nbr = nbr.next;
                }
            }
            tn = tn.next;
        }
        return maxm;
    }

    @Override
    public PointInterface[] CENTROID() {
        Point[] tr = new Point[components.size];
        ListNode<Component> cn = components.head;
        int i = 0;
        while (cn != null) {
            tr[i++] = getCentroid(cn.value);
            cn = cn.next;
        }
        // PointInterface[] tr = new PointInterface[centroids.size];
        // ListNode<PointInterface> ln = centroids.head;
        // int i = 0;
        // while (ln != null) {
        // tr[i++] = ln.value;
        // ln = ln.next;
        // }
        mergeSortPoint(tr, 0, tr.length-1);
        return tr;
    }

    @Override
    public PointInterface CENTROID_OF_COMPONENT(float[] point_coordinates) {
        Point p = new Point(point_coordinates[0], point_coordinates[1], point_coordinates[2]);
        RedBlackNode<Point, Point> pn = points.search(p);
        if (pn == null)
            return null;
        p = pn.value;
        Component c = p.triangles.head.value.isConnectedTo().component;
        return getCentroid(c);
    }

    Point getCentroid(Component c) {
        // RBTree<Point, Point> tpoints = new RBTree<Point, Point>();
        float x = 0, y = 0, z = 0;
        // ListNode<Triangle> tn = c.triangles.head;
        // while (tn != null) {
        // Triangle t = tn.value;
        // tpoints.insert(t.p1, t.p1);
        // tpoints.insert(t.p2, t.p2);
        // tpoints.insert(t.p3, t.p3);
        // tn = tn.next;
        // }
        // RedBlackNode<Point, Point> cn = tpoints.root;
        // LinkedList<RedBlackNode<Point, Point>> stack = new
        // LinkedList<RedBlackNode<Point, Point>>();
        // stack.add(cn);
        int s = 0;
        // while (stack.size > 0) {
        // RedBlackNode<Point, Point> en = stack.remove();
        // Point es = en.value;
        // x += es.x;
        // y += es.y;
        // z += es.z;
        // s++;
        // if (en.right.key != null) {
        // stack.add(en.right);
        // }
        // if (en.left.key != null) {
        // stack.add(en.left);
        // }
        // }
        // x /= s;
        // y /= s;
        // z /= s;
        // return new Point(x, y, z);
        ListNode<Triangle> tn = c.triangles.head;
        ListNode<Triangle> tt = c.triangles.head;
        while (tt != null) {
            tt.value.visited = false;
            tt.value.p1.visited = false;
            tt.value.p2.visited = false;
            tt.value.p3.visited = false;
            tt = tt.next;
        }
        LinkedList<Triangle> queue = new LinkedList<Triangle>();
        tn.value.visited = true;
        queue.endAdd(tn.value);
        while (queue.size > 0) {
            Triangle t = queue.remove();
            if (t.p1.visited == false) {
                x += t.p1.x;
                y += t.p1.y;
                z += t.p1.z;
                s++;
                t.p1.visited = true;
            }
            if (t.p2.visited == false) {
                x += t.p2.x;
                y += t.p2.y;
                z += t.p2.z;
                s++;
                t.p2.visited = true;
            }
            if (t.p3.visited == false) {
                x += t.p3.x;
                y += t.p3.y;
                z += t.p3.z;
                s++;
                t.p3.visited = true;
            }
            ListNode<Triangle> nbr = t.triangles.head;
            while (nbr != null) {
                if (nbr.value.visited == false) {
                    nbr.value.visited = true;
                    queue.endAdd(nbr.value);
                }
                nbr = nbr.next;
            }
        }
        x /= s;
        y /= s;
        z /= s;
        return new Point(x, y, z);
    }

    @Override
    public PointInterface[] CLOSEST_COMPONENTS() {
        int i = 0;
        Point p1 = new Point(0, 0, 0), p2 = new Point(0, 0, 0);
        double dist = -1;
        ListNode<Component> cn = components.head;
        LinkedList<Point>[] pointsList = new LinkedList[components.size];
        while (cn != null) {
            if (pointsList[i] == null) {
                pointsList[i] = cn.value.getPoints();
            }
            int j = i + 1;
            ListNode<Component> cn2 = cn.next;
            while (cn2 != null) {
                if (pointsList[j] == null) {
                    pointsList[j] = cn2.value.getPoints();
                }
                ListNode<Point> cn2point = pointsList[j].head;
                while (cn2point != null) {
                    ListNode<Point> cnpoint = pointsList[i].head;
                    while (cnpoint != null) {
                        double tdist = Math.sqrt(Math.pow((double) (cn2point.value.x - cnpoint.value.x), 2)
                                + Math.pow((double) (cn2point.value.y - cnpoint.value.y), 2)
                                + Math.pow((double) (cn2point.value.z - cnpoint.value.z), 2));
                        if (tdist <= dist || dist == -1) {
                            dist = tdist;
                            p1 = cn2point.value;
                            p2 = cnpoint.value;
                        }
                        if (dist == 0) {
                            System.out.println(dist);
                            return new PointInterface[] { p1, p2 };
                        }
                        cnpoint = cnpoint.next;
                    }
                    cn2point = cn2point.next;
                }
                j++;
                cn2 = cn2.next;
            }
            i++;
            cn = cn.next;
        }
        System.out.println(dist);
        return new PointInterface[] { p1, p2 };
        // return null;
    }

    public void mergeListEdge(Edge[] list, int s, int e) {
        int a = (s + e) / 2;
        Edge[] l1 = new Edge[a - s + 1];
        Edge[] l2 = new Edge[e - a];

        for (int i = 0; i < l1.length; i++) {
            l1[i] = list[s + i];
        }
        for (int j = 0; j < l2.length; j++) {
            l2[j] = list[a + 1 + j];
        }

        int l = 0, r = 0;
        int index = s;
        while(l < l1.length && r < l2.length){
            if (l1[l].length - l2[r].length <= 0){
                list[index++] = l1[l++];
            } else {
                list[index++] = l2[r++];
            }
        }
        while (l < l1.length){
            list[index++] = l1[l++];
        }
        while (r < l2.length){
            list[index++] = l2[r++];
        }
    }

    public void mergeSortEdge(Edge[] list, int s, int e) {
        if (s < e) {
            mergeSortEdge(list, s, (s + e) / 2);
            mergeSortEdge(list, (s + e) / 2 + 1, e);
            mergeListEdge(list, s, e);
        }
    }

    public void mergeListTriangle(Triangle[] list, int s, int e) {
        int a = (s + e) / 2;
        Triangle[] l1 = new Triangle[a - s + 1];
        Triangle[] l2 = new Triangle[e - a];

        for (int i = 0; i < l1.length; i++) {
            l1[i] = list[s + i];
        }
        for (int j = 0; j < l2.length; j++) {
            l2[j] = list[a + 1 + j];
        }

        int l = 0, r = 0;
        int index = s;
        while(l < l1.length && r < l2.length){
            if (l1[l].sno - l2[r].sno <= 0){
                list[index++] = l1[l++];
            } else {
                list[index++] = l2[r++];
            }
        }
        while (l < l1.length){
            list[index++] = l1[l++];
        }
        while (r < l2.length){
            list[index++] = l2[r++];
        }
    }

    public void mergeSortTriangle(Triangle[] list, int s, int e) {
        if (s < e) {
            mergeSortTriangle(list, s, (s + e) / 2);
            mergeSortTriangle(list, ((s + e) / 2) + 1, e);
            mergeListTriangle(list, s, e);
        }
    }

    public void mergeListPoint(Point[] list, int s, int e) {
        int a = (s + e) / 2;
        Point[] l1 = new Point[a - s + 1];
        Point[] l2 = new Point[e - a];

        for (int i = 0; i < l1.length; i++) {
            l1[i] = list[s + i];
        }
        for (int j = 0; j < l2.length; j++) {
            l2[j] = list[a + 1 + j];
        }

        int l = 0, r = 0;
        int index = s;
        while(l < l1.length && r < l2.length){
            if (l1[l].compareTo(l2[r]) <= 0){
                list[index++] = l1[l++];
            } else {
                list[index++] = l2[r++];
            }
        }
        while (l < l1.length){
            list[index++] = l1[l++];
        }
        while (r < l2.length){
            list[index++] = l2[r++];
        }
    }

    public void mergeSortPoint(Point[] list, int s, int e) {
        if (s < e) {
            mergeSortPoint(list, s, (s + e) / 2);
            mergeSortPoint(list, (s + e) / 2 + 1, e);
            mergeListPoint(list, s, e);
        }
    }
}