import java.io.*;
import java.util.*;
import java.lang.*;

public class G {

    private static void res(int l, int r, int[][] d, int[][] tmp, String str, StringBuilder resstr){
        if (d[l][r] == r - l + 1) return;

        if (d[l][r] == 0) {
            resstr.append(str.substring(l, r + 1));
            return;
        }

        if (tmp[l][r] == -1) {
            resstr.append(str.charAt(l));
            res(l+ 1, r-1, d, tmp, str, resstr);
            resstr.append(str.charAt(r));
            return;
        }
        res(l, tmp[l][r], d, tmp, str, resstr);
        res(tmp[l][r] + 1, r, d, tmp, str, resstr);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        StringBuilder resstr = new StringBuilder();;
        int n = str.length();

        int[][] d = new int[n][n];
        int[][] tmp = new int[n][n];

        for (int r = 0; r < n; r++) {
            for (int l = r; l >= 0; l--) {
                if (l== r) {
                    d[l][r] = 1;
                }
                else {
                    int best = 101;
                    int mk = -1;
                    if ((str.charAt(l) == '(' && str.charAt(r) == ')') || (str.charAt(l) == '[' && str.charAt(r) == ']') || (str.charAt(l) == '{' && str.charAt(r) == '}')) {
                        best= d[l+ 1][r-1];
                    }
                    for (int k = l; k < r; k++) {
                        if (d[l][k] + d[k + 1][r] < best) {
                            best = d[l][k] + d[k + 1][r];
                            mk = k;
                        }
                    }
                    d[l][r] = best;
                    tmp[l][r] = mk;
                }
            }
        }
        res(0, n -1, d, tmp, str, resstr);
        System.out.print(resstr.toString());
    }
}