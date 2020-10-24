import java.io.*;
import java.util.*;
import java.lang.*;

public class J {
    private static boolean poss(int p1, int p2, int n) {
        for (int i=0; i<n-1;i++){
            if ((((p1 >> i) & 1) == 1) && (((p1 >> (i + 1)) & 1) == 1) && (((p2 >> i) & 1) == 1) && (((p2 >> (i + 1)) & 1) == 1)) {
                return false;
            }
            if ((((p1 >> i) & 1) == 0) && (((p1 >> (i + 1)) & 1) == 0) && (((p2 >> i) & 1) == 0) && (((p2 >> (i + 1)) & 1) == 0)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] d = new int[1<<n][1<<n];
        int[][] a = new  int[m+2][1<<n];

        for (int p1=0; p1 < (1 <<n); p1++) {
            for (int p2=0; p2 < (1 << n); p2++) {
                if (poss(p1, p2, n)) {
                    d[p1][p2] = 1;
                }
            }
        }

        for (int p = 0; p < (1 << n); p++) {
            a[0][p] = 1;
        }

        for (int i=1; i< m; i++) {
            for (int p = 0; p < (1 << n); p++) {
                for (int r = 0; r < (1 << n); r++) {
                    a[i][p] += a[i - 1][r] * d[r][p];
                }

            }
        }

        int res=0;


        for (int p = 0; p < (1 << n); p++) {
            res += a[m-1][p];
        }

        System.out.println(res);
    }
}