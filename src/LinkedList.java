public class LinkedList<T> {
    ListNode<T> head;
    ListNode<T> tail;
    int size;

    LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    void add(T value) {
        ListNode<T> n = new ListNode<T>(value);
        n.next = head;
        head = n;
        if (size == 0)
            tail = head;
        size++;
    }

    void endAdd(T value) {
        ListNode<T> n = new ListNode<T>(value);
        size++;
        if (size == 1){
            tail = n;
            head = tail;
            return;
        }
        tail.next = n;
        tail = n;
    }

    T remove() {
        ListNode<T> n = head;
        head = head.next;
        size--;
        return n.value;
    }

    void remove(Component tr) {
        size--;
        ListNode<T> n = head;
        ListNode<T> p = null;
        while (n != null) {
            Component v = (Component) n.value;
            if (v.id == tr.id) {
                if(p == null){
                    head = n.next;
                } else {
                    p.next = n.next;
                }
                break;
            }
            p = n;
            n = n.next;
        }
    }
}

class ListNode<T> {
    T value;
    ListNode<T> next;

    ListNode(T value) {
        this.value = value;
    }
}