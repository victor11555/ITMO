import java.io.*;
import java.util.*;
import java.lang.*;

public class B {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m =  sc.nextInt();
        int[][] c = new int[n][m];
        int[][] d = new int[n][m];
        int[][] s = new int[n][m];
        StringBuilder sb = new StringBuilder();
        Integer cnt = 0;
        int max = 1;

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++) {
                c[i][j] = sc.nextInt();
            }
        }
        d[0][0] = c[0][0];
        for (int i = 1; i < n; i++){
            d[i][0] = d[i-1][0] + c[i][0];
            s[i][0] = -1;
        }
        for (int j = 1; j < m; j++) {
            d[0][j] = d[0][j-1] + c[0][j];
            s[0][j] = 1;
        }
        for (int i = 1; i < n; i++){
            for (int j = 1; j < m; j++) {
                if( d[i-1][j] >  d[i][j-1]) {
                    d[i][j] = d[i - 1][j] + c[i][j];
                    s[i][j] = -1;
                } else {
                    d[i][j] = d[i][j - 1] + c[i][j];
                    s[i][j] = 1;
                }
            }
        }
        int i = n-1;
        int j = m-1;
        for (int k=0; k<n+m-2;k++){
            if (s[i][j] == 1) {
                j--;
                sb.append("R");
            } else {
                i--;
                sb.append("D");
            }

        }
        sb.reverse();
        System.out.println(Integer.toString(d[n-1][m-1]));
        System.out.println(sb.toString());
    }
}
