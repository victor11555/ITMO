package queue;

public class ArrayQueueModule {
    private static int size, head;
    private static Object[] elements = new Object[10];

    private static int last_peace() {
        return (size + head) % elements.length;
    }

    private static Object[] newElements(int capacity) {
        Object[] newElements = new Object[capacity];
        if (head + size <= elements.length) {
            System.arraycopy(elements, head, newElements, 0, size);
        } else {
            System.arraycopy(elements, head, newElements, 0, size - last_peace());
            System.arraycopy(elements, 0, newElements, size - last_peace(), last_peace());
        }
        return newElements;
    }

    private static void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        }
        elements = newElements(2 * capacity);
        head = 0;
    }

    public static void enqueue(Object element) {
        assert element != null : "element == null";
        ensureCapacity(size + 1);
        elements[last_peace()] = element;
        size++;
    }

    public static Object element() {
        assert elements.length > 0 : "length == 0";
        return elements[head];
    }

    public static int size() {
        return size;
    }


    public static boolean isEmpty() {
        return size == 0;
    }

    public static void clear() {
        head = 0;
        size = 0;
        elements = new Object[10];
    }

    public static Object[] toArray() {
        return newElements(size);
    }

}
