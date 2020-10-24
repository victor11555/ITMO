import java.util.*;
import java.lang.*;

public class A {

    static class Node {
        int key;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
            right = null;
            left = null;
        }
    }

    public static class SearchTree {
        Node root;
    }

    private static Node insert(Node cur, int x) {
        if (cur == null) {
            return new Node(x);
        }

        if (x < cur.key) {
            cur.left = insert(cur.left, x);
        } else if (x > cur.key) {
            cur.right = insert(cur.right, x);
        } else {
            return cur;
        }

        return cur;
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

    private static int minimum(Node root) {
        return root.left == null ? root.key : minimum(root.left);
    }

    private static Node delete(Node cur, int x) {
        if (cur == null) {
            return null;
        }

        if (x == cur.key) {
            if (cur.left == null && cur.right == null) {
                return null;
            }
            if (cur.right == null) {
                return cur.left;
            }

            if (cur.left == null) {
                return cur.right;
            }
            int minim = minimum(cur.right);
            cur.key = minim;
            cur.right = delete(cur.right, minim);
            return cur;
        }
        if (x < cur.key) {
            cur.left = delete(cur.left, x);
            return cur;
        }
        cur.right = delete(cur.right, x);
        return cur;
    }

    public static void main(String[] args) {
        int res = 0, x;
        SearchTree tree = new SearchTree();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            x = sc.nextInt();
            switch (str) {
                case ("insert"):
                    tree.root = insert(tree.root, x);
                    break;
                case ("delete"):
                    tree.root = delete(tree.root, x);
                    break;
                case ("exists"):
                    System.out.println(exists(tree.root, x));
                    break;
                case ("next"):
                    System.out.println(next(tree.root, x));
                    break;
                default:
                    System.out.println(prev(tree.root, x));
            }
        }

    }
}
