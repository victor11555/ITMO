import java.util.*;
import java.io.*;

public class N {
    public static class Node {
        private String value;
        private Node prev;
        private Node next;

        public Node(String value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

    }

    public static Node last = null;

    public static void main(String[] args) throws IOException {
        File fout = new File("linkedmap.out");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        Map<String, Node> set = new HashMap<String, Node>(1000000);
        Scanner sc = new Scanner(new File("linkedmap.in"));
        while (sc.hasNext()) {
            String op = sc.next();
            String x = sc.next();
            String y;
            Node q = set.get(x);
            switch (op) {
                case "put":
                    y = sc.next();
                    if(q == null) {
                        Node tmp = new Node(y, last, null);
                        if (last != null) {
                            last.next = tmp;
                        }
                        last = tmp;
                        set.put(x, tmp);
                    }
                    else{
                        q.value = y;
                    }
                    break;

                case"get":
                    if (q != null) {
                        bw.write(q.value);
                        bw.newLine();
                    }
                    else
                        bw.write("none\n");
                    break;

                case "prev":
                    if (q == null){
                        bw.write("none\n");
                    }
                    else if (q.prev == null) {
                        bw.write("none\n");
                    } else {
                        bw.write(q.prev.value);
                        bw.newLine();
                    }
                    break;

                case "next":
                    if (q == null){
                        bw.write("none\n");
                    }
                    else if (q.next == null) {
                        bw.write("none\n");
                    } else {
                        bw.write(q.next.value);
                        bw.newLine();
                    }
                    break;

                case "delete":
                    if(q != null) {
                        if (q.next != null) {
                            q.next.prev = q.prev;
                        }
                        if (q.prev != null) {
                            q.prev.next = q.next;
                        }
                        if (last == q) {
                            last = q.prev;
                        }
                        set.remove(x);
                    }
                    break;

            }
        }
        sc.close();
        bw.close();
        fos.close();
    }
}