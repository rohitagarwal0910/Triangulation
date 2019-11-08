public class Component {
    // ComponentId idObject;
    int id;
    LinkedList<Triangle> triangles = new LinkedList<Triangle>();
    LinkedList<Point> points = new LinkedList<Point>();

    Component(int id) {
        // idObject = new ComponentId(id);
        this.id = id;
    }

    LinkedList<Point> getPoints() {
        LinkedList<Point> tr = new LinkedList<Point>();
        // RBTree<Point, Point> tpoints = new RBTree<Point, Point>();
        // ListNode<Triangle> tn = this.triangles.head;
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
        // while (stack.size > 0) {
        // RedBlackNode<Point, Point> en = stack.remove();
        // Point es = en.value;
        // tr.add(es);
        // if (en.right.key != null) {
        // stack.add(en.right);
        // }
        // if (en.left.key != null) {
        // stack.add(en.left);
        // }
        // }
        // return tr;
        ListNode<Triangle> tn = triangles.head;
        ListNode<Triangle> tt = triangles.head;
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
                tr.add(t.p1);
                t.p1.visited = true;
            }
            if (t.p2.visited == false) {
                tr.add(t.p2);
                t.p2.visited = true;
            }
            if (t.p3.visited == false) {
                tr.add(t.p3);
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
        return tr;
    }
}

// class ComponentId implements Comparable<ComponentId> {
// int id;

// ComponentId(int id) {
// this.id = id;
// }

// @Override
// public int compareTo(ComponentId o) {
// return id - o.id;
// }
// }