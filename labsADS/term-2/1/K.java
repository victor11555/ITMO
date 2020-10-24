import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.lang.*;


public class K {
    public static int[] minarr;
    public static int k, n;

    static int min(int v, int l, int r, int a, int b) {
        if (a >= r || l >= b) { return n; }
        if (l >= a && r <= b) { return minarr[v]; }
        int m = (l + r)/2;
        int sl = min(2 * v + 1, l, m, a, b);
        int sr = min(2 * v + 2, m, r, a, b);
        return Math.min(sl, sr);
    }

    static void update(int i, int x) {
        minarr[k - 1 + i] = x;
        int j = k - 1 + i;
        while (j > 0) {
            j = (j - 1) / 2;
            minarr[j] = Math.min(minarr[2 * j + 1], minarr[2 * j + 2]);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> results = new ArrayList<>();
        int x;
        Scanner sc = new Scanner(new File("parking.in"));
        n = sc.nextInt();
        int m = sc.nextInt();

        k = 1;
        while (k < n) {
            k *= 2;
        }

        minarr = new int[2 * k - 1];
        for (int i = 0; i < n; i++) {
            minarr[k - 1 + i] = i;
        }
        for (int i = n; i < k; i++) {
            minarr[k - 1 + i] = n;
        }
        for (int i = k - 2; i >= 0; i--) {
            minarr[i] = Math.min(minarr[2 * i + 1], minarr[2 * i + 2]);
        }

        for (int i = 0; i < m; i++) {
            String str = sc.next();
            x = sc.nextInt();
            if (str.equals("enter")) {
                int res = min(0, 0, k, x - 1, k);
                if (res >= n) {
                    res = min(0, 0, k, 0, x);
                }
                update(res, n);
                results.add(res+1);
            } else {
                update(x-1, x-1);
            }
        }

        try (FileWriter writer = new FileWriter("parking.out")) {
            for (int i = 0; i < results.size(); i++) {
                String str1 = Integer.toString(results.get(i));
                writer.write(str1);
                writer.write(System.lineSeparator());
            }
        } catch (IOException ex) {
        }

    }
}
