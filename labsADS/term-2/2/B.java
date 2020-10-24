import java.util.Random;
import java.util.Scanner;

public class B {
    private static final Random random =  new Random();

    static class Node {
        int key;
        int prior;
        Node left;
        Node right;

        Node(int key, int prior) {
            this.key = key;
            this.prior = prior;
            right = null;
            left = null;
        }
        Node(int key, int prior, Node left, Node right) {
            this.key = key;
            this.prior = prior;
            this.right = right;
            this.left = left;
        }
    }

    public static class Treap {
        Node root;
    }

    private static Node merge(Node L, Node R){
        if (R == null) return L;
        if (L == null) return R;
        else if (L.prior > R.prior) {
            L.right = merge(L.right, R);
            return L;
        } else {
            R.left = merge(L, R.left);
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
        return newTree;
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

    private static boolean exists(Node cur, int x) {
        if (cur == null) {
            return false;
        }
        if (x == cur.key) {
            return true;
        }
        return x < cur.key ? exists(cur.left, x) : exists(cur.right, x);
    }

    private static String next(Node cur, int x) {
        String successor = "none";
        while (cur != null) {
            if (cur.key > x) {
                successor = cur.key + "";
                cur = cur.left;
            }else {cur = cur.right; }
        }
        return successor;
    }

    private static String prev(Node cur, int x) {
        String successor = "none";
        while (cur != null) {
            if (cur.key < x) {
                successor = cur.key + "";
                cur = cur.right;
            }else {cur = cur.left; }
        }
        return successor;
    }


    public static void main(String[] args) {
        int res = 0, x;
        Treap treap = new Treap();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            x = sc.nextInt();
            switch (str) {
                case ("insert"):
                    treap.root = insert(treap.root , new Node(x, random.nextInt()));
                    break;
                case ("delete"):
                    treap.root = delete(treap.root , x);
                    break;
                case ("exists"):
                    System.out.println(exists(treap.root, x));
                    break;
                case ("next"):
                    System.out.println(next(treap.root, x));
                    break;
                default:
                    System.out.println(prev(treap.root, x));
            }
        }
    }
}
