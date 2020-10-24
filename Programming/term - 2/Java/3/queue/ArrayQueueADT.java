package queue;

// Invar: n ≥ 0 ∧ ∀i=1..n: a[i] ≠ null
// n - size    a[1]..a[n] - queue
public class ArrayQueueADT {
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[16];

    private static void ensureCapacity(ArrayQueueADT queue, int size) {
        if (size == queue.elements.length || (size > 3 && size == queue.elements.length / 4)) {
            Object[] newElements;
            if (size == queue.elements.length) {
                newElements = new Object[queue.elements.length * 2];
            } else {
                newElements = new Object[queue.elements.length / 2];
            }
            if (queue.head <= queue.tail) {
                System.arraycopy(queue.elements, queue.head, newElements, 0, size(queue));
            } else {
                System.arraycopy(queue.elements, queue.head, newElements, 0, queue.elements.length - queue.head);
                System.arraycopy(queue.elements, 0, newElements, queue.elements.length - queue.head, queue.tail);
            }
            queue.tail = size(queue);
            queue.head = 0;
            queue.elements = newElements;
        }
    }

    // Pre:  element != null
    public static void enqueue(ArrayQueueADT queue, Object element) {
        ensureCapacity(queue, size(queue) + 1);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
    }
    // Post: a'[n + 1] = element ∧ ∀i=1..n : a'[i] = a[i] ∧ n' = n + 1

    
    // Pre:  n > 0
    public static Object element(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        return queue.elements[queue.head];
    }
    // Post: ∀i=1..n : a'[i] = a[i] ∧ n' = n ∧ R = a[1]

    
    // Pre:  n > 0
    public static Object dequeue(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        ensureCapacity(queue, size(queue) - 1);
        Object r = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        return r;
    }
    // Post: R = a[1] ∧ n' = n - 1 ∧ ∀i=1..n-1 ∧ a'[i] = a[i+1]

   
    public static int size(ArrayQueueADT queue) {
        return queue.tail - queue.head + (queue.head > queue.tail ? queue.elements.length : 0);
    }
    // Post: ℝ = n ∧ n' = n ∧ ∀i=1..n : a'[i] = a[i]

    
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }
    // Post: ℝ = n > 0 ∧ n' = n ∧ ∀i=1..n : a'[i] = a[i]

    
    public static void clear(ArrayQueueADT queue) {
        while (!isEmpty(queue)) {
            dequeue(queue);
        }
    }
    // Post: n' = 0


    // Pre:  n > 0
    public static String toStr(ArrayQueueADT queue) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size(queue); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(queue.elements[(queue.head + i) % queue.elements.length]);
        }
        sb.append("]");
        return sb.toString();
    }
    // Post: R = [a[1], ..., a[n]] -> string ∧ n' = n ∧ ∀i=1..n : a'[i] = a[i]

}