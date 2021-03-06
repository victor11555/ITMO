import java.util.Random;
import java.util.Scanner;

public class F {
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
            this.left = null;
            this.right = null;
        }

        Node(int key, int prior, int weight, Node left, Node right) {
            this.key = key;
            this.prior = prior;
            this.weight = weight;
            this.left = left;
            this.right = right;
        }
    }

    public static class Treap {
        Node root;
    }

    public static void recalc(Node T) {
        T.weight = (T.left == null ? 0 : T.left.weight) + (T.right == null ? 0 : T.right.weight) + 1;
    }

    public static Node[] split(Node T, int x) {
        if (T == null) {
            Node[] p = new Node[2];
            p[0] = null;
            p[1] = null;
            return  p;
        }
        Node[] newTree = new Node[2];
        int curIndex = (T.left == null ? 0 : T.left.weight) + 1;
        if (curIndex <= x) {
            Node[] p = split(T.right, x - curIndex);
            T.right = p[0];
            newTree[0] = T;
            newTree[1] = p[1];
            recalc(newTree[0]);
        } else {
            Node[] p = split(T.left, x);
            T.left = p[1];
            newTree[0] = p[0];
            newTree[1] = T;
            recalc(newTree[1]);
        }
        return newTree;
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

    public static Node add(Node T, Node node, int pos) {
        Node[] p = split(T, pos);
        p[0] = merge(p[0], node);
        return merge(p[0], p[1]);
    }

    public static Node delete(Node T, int pos) {
        Node[] p = split(T, pos - 1);
        Node[] p1 = split(p[1], 1);
        p[1] = p1[1];
        return merge(p[0], p[1]);
    }

    public static void inorderPrint(Node root) {
        if (root == null) {
            return;
        }
        inorderPrint(root.left);
        System.out.print(root.key + " ");
        inorderPrint(root.right);
    }

    public static void main(String[] args) {
        int m, n0, k, x;
        Treap treap = new Treap();
        Scanner sc = new Scanner(System.in);
        n0 = sc.nextInt();
        m = sc.nextInt();
        for (int i = 1; i <= n0; i++) {
            x = sc.nextInt();
            treap.root = add(treap.root, new Node(x, random.nextInt(), 1), i);
        }
        for (int i = 0; i < m; i++) {
            String str = sc.next();
            switch (str) {
                case ("add"):
                    k = sc.nextInt();
                    x = sc.nextInt();
                    treap.root = add(treap.root, new Node(x, random.nextInt(), 1), k);
                    break;
                case ("del"):
                    x = sc.nextInt();
                    treap.root = delete(treap.root, x);
                    break;
            }
        }
        System.out.println(treap.root == null ? 0 : treap.root.weight);
        inorderPrint(treap.root);
    }
}
