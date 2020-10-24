import java.util.Random;
import java.util.Scanner;

public class B {
    private static final Random random =  new Random();

    static class Node {
        int key;
        int prior;
        int weight;
        Node left;
        Node right;

        Node(int key, int prior, int weight) {
            this.key = key;
            this.prior = prior;
            this.weight = weight;
            right = null;
            left = null;
        }
        Node(int key, int prior, int weight,  Node left, Node right) {
            this.key = key;
            this.prior = prior;
            this.weight = weight;
            this.right = right;
            this.left = left;
        }
    }

    public static class Treap {
        Node root;
    }

    public static void recalc(Node T){
        if(T == null) return;
        T.weight = (T.left == null ? 0 : T.left.weight) + (T.right == null ? 0 : T.right.weight) + 1;
    }

    private static Node merge(Node L, Node R){
        if (R == null) return L;
        if (L == null) return R;
        else if (L.prior > R.prior) {
            L.right = merge(L.right, R);
            recalc(L);
            return L;
        } else {
            R.left = merge(L, R.left);
            recalc(R);
            return R;
        }
    }

    private static Node[] split(Node T, int x) {
        if (T == null) {
            Node[] p = new Node[2];
            p[0] = null;
            p[1] = null;
            return  p;
        }
        Node[] newTree = new Node[2];
        if (T.key < x) {
            Node[] p = split(T.right, x);
            T.right = p[0];
            newTree[0] = T;
            newTree[1] = p[1];
        } else {
            Node[] p = split(T.left, x);
            T.left = p[1];
            newTree[0] = p[0];
            newTree[1] = T;
        }
        recalc(newTree[0]);
        recalc(newTree[1]);
        return newTree;
    }

    private static boolean exists(Node cur, int x) {
        if (cur == null) {
            return false;
        }
        if (x == cur.key) {
            return true;
        }
        return x < cur.key ? exists(cur.left, x) : exists(cur.right, x);
    }

    private static Node insert(Node A, Node node) {
        if(exists(A, node.key)){
            return A;
        }
        Node[] p = split(A, node.key);
        p[0] = merge(p[0], node);
        return merge(p[0], p[1]);
    }

    private static Node delete(Node A, int x){
        if(!exists(A, x)){
            return A;
        }
        Node[] p = split(A, x);
        Node[] p1 = split(p[1], x + 1);
        p[1] = p1[1];
        return merge(p[0], p[1]);
    }

    public static Node k_max(Node T, int k) {
        if (T.right == null) return (k == 1) ? T : k_max(T.left, k - 1);
        int weight = T.right.weight;
        if (k == weight+1) return T;
        if (k > weight+1) return k_max(T.left, k - weight-1);
        return k_max(T.right, k);
    }
    public static void main(String[] args) {
        int m, n, k;
        String op;
        Treap treap = new Treap();
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            String str = sc.next();
            k = sc.nextInt();
            switch (str) {
                case ("+1"):
                    treap.root = insert(treap.root , new Node(k, random.nextInt(), 1));
                    break;
                case ("1"):
                    treap.root = insert(treap.root , new Node(k, random.nextInt(), 1));
                    break;
                case ("-1"):
                    treap.root = delete(treap.root, k);
                    break;
                case ("0"):
                    System.out.println(k_max(treap.root, k).key);
                    break;
            }
        }
    }
}
