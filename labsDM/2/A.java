import java.io.*;
import java.util.*;

public class A {

    static class Node implements Comparable<Node> {
        final long result;
        public Node (long result){ this.result = result; }
        @Override
        public int compareTo(Node tmp) { return Long.compare(result, tmp.result);}
    }

    static class InternalNode extends Node{
        Node lchild;
        Node rchild;
        public InternalNode (Node lchild, Node rchild){
            super(lchild.result + rchild.result);
            this.lchild = lchild;
            this.rchild = rchild;
        }
    }

    static class LeafNode extends Node {
        public LeafNode(long frequency){super(frequency);}
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("huffman.in"));
        int n = sc.nextInt();
        if (n == 1) {
            try (FileWriter writer = new FileWriter("huffman.out")) {
                Integer x =sc.nextInt();
                writer.write(x.toString());
            } catch (IOException ex) {
            }
        } else {
            ArrayList<Long> frequency = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                frequency.add(sc.nextLong());
            }
            PriorityQueue<Node> pq = new PriorityQueue<>();
            for (int i = 0; i < frequency.size(); i++) {
                pq.add(new LeafNode(frequency.get(i)));
            }
            Long result = 0L;
            while (pq.size() > 1) {
                InternalNode node = new InternalNode(pq.poll(), pq.poll());
                result += node.result;
                pq.add(node);
            }
            try (FileWriter writer = new FileWriter("huffman.out")) {
                writer.write(result.toString());
            } catch (IOException ex) {
            }
        }
    }
}