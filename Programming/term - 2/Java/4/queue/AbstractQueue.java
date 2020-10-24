package queue;

import java.util.Arrays;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object element) {
        assert element != null : "element == null";
        enqueueFull(element);
        ++size;
    }

    public Object element() {
        assert size > 0 : "size == 0";
        return getElements();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object dequeue() {
        assert size > 0;
        Object el = element();
        dequeueFull();
        --size;
        return el;
    }

    public void clear() {
        size = 0;
        clearFull();
    }

    public Object[] toArray() {
        return newElements(size);
    }

    public String toStr() {
        return Arrays.toString(newElements(size));
    }

    protected Object[] newElements(int capacity) {
        Object[] result = new Object[capacity];
        for (int i = 0; i < size; ++i) {
            result[i] = element();
            enqueue(dequeue());
        }
        return result;
    }

    protected abstract void clearFull();

    protected abstract void dequeueFull();

    protected abstract Object getElements();

    protected abstract void enqueueFull(Object element);
}
