public class RedBlackNode<T extends Comparable<T>, E> {
    public T key;
    public E value;
    boolean b;
    public RedBlackNode<T, E> left = null;
    public RedBlackNode<T, E> right = null;
    RedBlackNode<T, E> parent = null;

    RedBlackNode(T key, E value) {
        if (key != null) {
            this.key = key;
            this.value = value;
            left = new RedBlackNode<T,E>(null, null);
            right = new RedBlackNode<T,E>(null, null);
            left.parent = this;
            right.parent = this;
        }
    }
}
