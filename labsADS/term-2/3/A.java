import java.util.*;
import java.lang.*;

public class A {

    public static int qwe(int n) {
        int l = 1;
        while ((1 << l) < n) {
            l++;
        }
        return l;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int l = qwe(n);
        int[][] jump = new int[n + 1][l];
        for (int i = 1; i < n + 1; i++) {
            jump[i][0] = sc.nextInt();
        }
        for (int j = 0; j < l-1; j++) {
            for (int i = 1; i <= n; i++) {
                if (jump[i][j] == 0) {
                    jump[i][j+1] = 0;
                } else {
                    jump[i][j+1] = jump[jump[i][j]][j];
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(i + ":");
            if (jump[i][0] != 0) {
                sb.append(" " + jump[i][0]);
                for (int j = 1; j < l; j++) {
                    if (jump[i][j - 1] == 0) {
                        break;
                    } else {
                        if (jump[i][j] != 0) {
                            sb.append(" " + jump[i][j]);
                        }
                    }
                }
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
