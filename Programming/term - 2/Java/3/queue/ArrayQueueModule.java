package queue;

// Invar: n ≥ 0 ∧ ∀i=1..n: a[i] ≠ null
//       n - size    a[1]..a[n] - queue
public class ArrayQueueModule {
    private static int head = 0;
    private static int tail = 0;
    private static Object[] elements = new Object[16];

    private static void ensureCapacity(int size) {
        if (size == elements.length || (size > 3 && size == elements.length / 4)) {
            Object[] newElements;
            if (size == elements.length) {
                newElements = new Object[elements.length * 2];
            } else {
                newElements = new Object[elements.length / 2];
            }
            if (head <= tail) {
                System.arraycopy(elements, head, newElements, 0, size());
            } else {
                System.arraycopy(elements, head, newElements, 0, elements.length - head);
                System.arraycopy(elements, 0, newElements, elements.length - head, tail);
            }
            tail = size();
            head = 0;
            elements = newElements;
        }
    }

    
    // Pre:  element != null
    public static void enqueue(Object element) {
        ensureCapacity(size() + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }
    // Post: a'[n + 1] = element ∧ ∀i=1..n : a'[i] = a[i] ∧ n' = n + 1

    
    // Pre:  n > 0
    public static Object element() {
        assert !isEmpty();
        return elements[head];
    }
    // Post: ∀i=1..n : a'[i] = a[i] ∧ n' = n ∧ R = a[1]

    
    // Pre:  n > 0
    public static Object dequeue() {
        assert !isEmpty();
        ensureCapacity(size() - 1);
        Object r = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return r;
    }
    // Post: R = a[1] ∧ n' = n - 1 ∧ ∀i=1..n-1 ∧ a'[i] = a[i+1]

    
    public static int size() {
        return tail - head + (head > tail ? elements.length : 0);
    }
    // Post: ℝ = n ∧ n' = n ∧ ∀i=1..n : a'[i] = a[i]
    

    public static boolean isEmpty() {
        return size() == 0;
    }
    // Post: ℝ = n > 0 ∧ n' = n ∧ ∀i=1..n : a'[i] = a[i]
    
    
    public static void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }
    // Post: n' = 0

    
    // Pre:  n > 0
    public static String toStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size(); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(elements[(head + i) % elements.length]);
        }
        sb.append("]");
        return sb.toString();
    }
    // Post: R = [a[1], ..., a[n]] -> string ∧ n' = n ∧ ∀i=1..n : a'[i] = a[i]

}