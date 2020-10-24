package queue;

public class ArrayQueueADT {
    private int size, head;
    private Object[] elements = new Object[10];

    private static int last_peace(ArrayQueueADT queue) {
        return (queue.size + queue.head) % queue.elements.length;
    }

    private static Object[] newElements(ArrayQueueADT queue, int capacity) {
        Object[] newElements = new Object[capacity];
        if (queue.head + queue.size <= queue.elements.length) {
            System.arraycopy(queue.elements, queue.head, newElements, 0, queue.size);
        } else {
            System.arraycopy(queue.elements, queue.head, newElements, 0, queue.size - last_peace(queue));
            System.arraycopy(queue.elements, 0, newElements, queue.size - last_peace(queue), last_peace(queue));
        }
        return newElements;
    }

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity <= queue.elements.length) {
            return;
        }
        queue.elements = newElements(queue, 2 * capacity);
        queue.head = 0;
    }

    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert queue != null : "queue == null";
        assert element != null : "element == null";
        ensureCapacity(queue, queue.size + 1);
        queue.elements[last_peace(queue)] = element;
        queue.size++;
    }

    public static Object element(ArrayQueueADT queue) {
        assert queue != null : "queue == null";
        assert queue.elements.length > 0 : "length == 0";
        return queue.elements[queue.head];
    }

    public static Object dequeue(ArrayQueueADT queue) {
        assert queue != null : "queue == null";
        assert queue.elements.length > 0 : "length == 0";
        Object tmp = queue.elements[queue.head];
        queue.elements[queue.head] = null; //memory leak
        queue.head++;
        if (queue.head == queue.elements.length) queue.head = 0;
        queue.size--;
        return tmp;
    }

    public static int size(ArrayQueueADT queue) {
        assert queue != null : "queue == null";
        return queue.size;
    }

    public static boolean isEmpty(ArrayQueueADT queue) {
        assert queue != null : "queue == null";
        return queue.size == 0;
    }

    public static void clear(ArrayQueueADT queue) {
        assert queue != null : "queue == null";
        queue.head = 0;
        queue.size = 0;
        queue.elements = new Object[10];
    }

    public static Object[] toArray(ArrayQueueADT queue) {
        assert queue != null : "queue == null";
        return newElements(queue, queue.size);
    }
}
