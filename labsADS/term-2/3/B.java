import java.util.*;
import java.lang.*;
public class B {

    public static int qwe(int n) {
        int l = 1;
        while ((1 << l) < n) {
            l++;
        }
        return l;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int l = qwe(n);
        int[][] jump = new int[n + 1][l];
        int[] d = new int[n + 1];
        d[1] = 0;
        ArrayList<ArrayList<Integer>> ancestors = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            ancestors.add(new ArrayList<>());
            if(i > 1) {
                jump[i][0] =  sc.nextInt();;
                ancestors.get(jump[i][0]).add(i);
            }
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < ancestors.get(i).size(); j++) {
                d[ancestors.get(i).get(j)] = d[i] + 1;
            }
        }

        for (int j = 1; j < l; j++) {
            for (int i = 1; i < n+1; i++) {
                int y = jump[i][j - 1];
                if (y == 0) {
                    jump[i][j] = 0;
                } else {
                    jump[i][j] = jump[y][j - 1];
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            if (d[u] < d[v]) {
                int tmp = u;
                u = v;
                v = tmp;
            }
            int D = d[u] - d[v];
            for (int j = l; j >= 0; j--) {
                if (D >= (1 << j)) {
                    u = jump[u][j];
                    D -= (1 << j);
                }
            }
            if (u == v) {
                sb.append(u);
            } else {
                for (int j = l-1; j >= 0; j--) {
                    if (jump[u][j] != jump[v][j]) {
                        u = jump[u][j];
                        v = jump[v][j];
                    }
                }
                sb.append(jump[u][0]);
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
