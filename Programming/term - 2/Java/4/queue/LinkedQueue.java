package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    public LinkedQueue() {
        size = 0;
        head = tail = null;
    }

    public class Node {
        private Object value;
        private Node next;

        public Node(Object value) {
            assert value != null : "value == null";
            this.value = value;
            this.next = null;
        }
    }


    public void enqueueFull(Object element) {
        if (tail == null) {
            head = tail = new Node(element);
        } else {
            tail.next = new Node(element);
            tail = tail.next;
        }
    }


    public Object getElements() {
        return head.value;
    }


    public void dequeueFull() {
        head = head.next;
        if (size - 1 == 0) tail = null;
    }

    protected void clearFull() {
        head = tail = null;
    }


}
