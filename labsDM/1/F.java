import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;
public class F {
        public static void main(String[] args) {
                Scanner sc = new Scanner(System.in);
                int n, m = 1;
                n = sc.nextInt();
                for (int i = 1; i <= n; i++){
                        m *=2;
                }
                String [] a = new String [m];
                int [][] b = new int [m][m];
                for (int i = 0; i < m; i++) {
                        a[i] = sc.next();
                        b[i][0] = sc.nextInt() ;
                }

                for (int i = 1; i < m; i++) {
                        for (int j = 0; j < m - i; j++) {
                                b[j][i] = (b[j][i - 1] + b[j + 1][i - 1]) % 2;
                        }
                }
                for (int i = 0; i < m; i++) {
                        System.out.println(a[i] + " " + b[0][i]);
                }
        }
}