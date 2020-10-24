import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Bag {
    private Map<Object, Integer> storage;

    public Bag() {
        this.storage = new ConcurrentHashMap<>();
    }

    public void add(Object o) {
        Integer count = this.storage.get(o);
        if (count == null) {
            this.storage.put(o, 1);
        } else {
            this.storage.put(o, count+1);
        }
    }

    public void remove(Object o) {
        this.storage.remove(o);
    }

    public void intersect(Bag b) {
        Map<Object, Integer> newStorage = new ConcurrentHashMap<>(b.storage);

        for (Map.Entry<Object, Integer> newentry : newStorage.entrySet()) {
            Object obj = newentry.getKey();
            Integer rcount = this.storage.get(obj);
            if (rcount != null) {
                int lcount = newentry.getValue();
                int count = Math.min(lcount, rcount);
                if (count != 0) {
                    newStorage.put(obj, count);
                } else {
                    newStorage.remove(obj);
                }
            } else {
                newStorage.remove(obj);
            }
        }
        this.storage = newStorage;
    }

    public void union(Bag b) {
        for (Map.Entry<Object, Integer> rentry : b.storage.entrySet()) {
            Object key = rentry.getKey();
            int rcount = rentry.getValue();
            if (this.storage.containsKey(key)) {
                int lcount = this.storage.get(key);
                this.storage.put(key, lcount + rcount);
            } else {
                this.storage.put(key, rcount);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Bag [");
        for (Map.Entry<Object, Integer> entry : storage.entrySet()) {
            sb.append(entry.getKey().toString())
                    .append(":")
                    .append(entry.getValue())
                    .append(", ");
        }
        int trimIx = sb.length() == 5 ? 0 : 2;
        return sb.substring(0, sb.length()-trimIx)+"]";
    }

}
