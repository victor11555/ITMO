package queue;


public class ArrayQueue extends AbstractQueue {
    private int head;
    private Object[] elements = new Object[10];

    private int last_peace() {
        return (size + head) % elements.length;
    }

    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        }
        elements = newElements(2 * capacity);
        head = 0;
    }


    public void enqueueFull(Object element) {
        ensureCapacity(size + 1);
        elements[last_peace()] = element;
    }

    public Object getElements() {
        return elements[head];
    }

    public void dequeueFull() {
        elements[head] = null;
        ++head;
        if (head == elements.length) head = 0;
    }

    protected void clearFull() {
        size = 0;
        elements = new Object[10];
    }
}
