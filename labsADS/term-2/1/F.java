import java.util.Scanner;
import java.lang.*;

public class F{

    static int[][] st;
    static int[] precalc;
    static int k;

    static long min(int l, int r){
        k = precalc[(r - l + 1)];
        return Math.min(st[l-1][k], st[(int)(r - Math.pow(2, k))][k]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] a = new int[n];
        precalc = new int[n+1];
        long  res = 0;
        int u = 0, v = 0, l = 0, r = 0;
        a[0] = sc.nextInt();
        for (int i = 1; i < n; i++) {
            a[i] = (23 * a[i - 1] + 21563) % 16714589;
        }
        precalc[0] = 0;
        for (int i = 1; i <= n; i++) {
            precalc[i] = precalc[i-1];
            if((1 << (precalc[i] + 1)) == i){
                precalc[i]++;
            }
        }

        st = new int[n][precalc[n] + 1];
        for (int i = 0; i < n; i++) {
            st[i][0] = a[i];
        }
        for (int j = 1; j <= precalc[n]; j++) {
            for (int i = 0; (i + (int)Math.pow(2, j)) <= n; i++) {
                st[i][j] = Math.min(st[i][j-1], st[i+(int)Math.pow(2, j-1)][j-1]);
            }
        }
        u = sc.nextInt();
        v = sc.nextInt();
        l = Math.min(u, v);
        r = Math.max(u, v);
        res = min(l, r);

        for (int i = 2; i <= m; i++) {
            u = (int)((17 * u + 751 + res + 2 * (i-1)) % n) + 1;
            v = (int)((13 * v + 593 + res + 5 * (i-1)) % n) + 1;
            l = Math.min(u, v);
            r = Math.max(u, v);
            res = min(l, r);
        }
        System.out.println(u + " " + v + " " + res);
    }
}
