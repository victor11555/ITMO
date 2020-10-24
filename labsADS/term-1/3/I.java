import java.io.*;
import java.util.*;
import java.lang.*;

public class I {
    private static void Dyn(int p, int prof, int len, int[][] d, int m) {
        if (len == m) {
            d[p][prof]=1;
            return;
        }
        if (((p >> len) & 1) == 0) {
            Dyn(p, prof + (1 << len), len + 1, d, m);
            if (len < m - 1) {
                if (((p >> (len + 1)) & 1) == 0) {
                    Dyn(p, prof, len + 2, d, m);
                }
            }
        } else {
            Dyn(p, prof, len + 1, d, m);
        }

    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();

        int[] init = new int[n+2];
        int[][] d = new int[1<<m][1<<m];
        int[][] a = new  int[n+2][1<<m];

        for (int i = 0; i < n; i++) {
            String str = sc.nextLine();
            for (int j = 0; j < m; j++) {
                if (str.charAt(j) != '.') {
                    init[i] += (1<<j);
                }
            }
        }

        for (int p=0; p< (1 << m); p++) {
            Dyn(p, 0, 0, d, m);
        }
        a[1][0]=1;

        for (int i=2; i< n+2; i++) {
            for (int p = 0; p < (1 << m); p++) {
                if ((p&init[i-1]) == 0) {
                    for (int r = 0; r < (1 << m)-init[i-2]; r++) {
                        a[i][p] += a[i - 1][r] * d[r+init[i-2]][p];
                    }
                }
            }
        }
        System.out.println(a[n+1][0]);
    }
}