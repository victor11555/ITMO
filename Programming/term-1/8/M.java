import java.io.*;
import java.util.*;

public class M{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        Vector<Integer>[] a = (Vector<Integer>[]) new Vector[t];
        Map<Integer, Integer>[] c = (HashMap<Integer, Integer>[]) new HashMap[t];
        int[] res = new int[t];


        for (int i = 0; i < t; i++) {
            int n = sc.nextInt();
            a[i] = new Vector<Integer>();
            c[i] = new HashMap<Integer, Integer>();
            for (int j = 0; j < n; j++) {
                a[i].add(sc.nextInt());
            }
        }

        for (int i = 0; i < t; i++) {
            for (int j = a[i].size() - 2; j > 0 ; j--) {
                if (c[i].containsKey(a[i].get(j + 1))) {
                    c[i].put(a[i].get(j + 1), c[i].get(a[i].get(j + 1)) + 1);
                } else {c[i].put(a[i].get(j + 1),1);}
                for (int g = 0; g < j; g++) {
                    if (c[i].containsKey(2 * a[i].get(j) - a[i].get(g))) {
                        res[i] += c[i].get(2 * a[i].get(j) - a[i].get(g));
                    }
                }
            }
        }
        for (int i = 0; i < t; i++) {
            System.out.println(res[i]);
        }
    }
}