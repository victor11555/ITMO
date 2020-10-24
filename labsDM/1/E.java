import java.util.Scanner;
import java.lang.Math;
import java.util.Vector;

public class E {
    private static boolean nextComb(int[] arr){
        int n = arr.length;
        for( int i = n - 1; i>=0; i--) {
            if (arr[i] < 1) {
                arr[i]++;
                return true;
            } else {
                arr[i] = 0;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int [][] trtbl = new int[28][32];
        int [] result = new int[28];
        int [] depth = new int[28];

        Vector<Integer> nodes = new Vector<>();
        Vector<Integer> leaves = new Vector<>();

        Vector<Integer>[] scheme = (Vector<Integer>[]) new Vector[28];
        for(int i = 0; i < 28; i++)
            scheme[i] = new Vector<Integer>();


        Scanner sc = new Scanner(System.in);
        int n, tmp;
        n = sc.nextInt();
        for (int i = 1; i <= n; ++i) {
            int cur;
            cur = sc.nextInt();
            if (cur != 0) {
                nodes.add(i);
                for (int j = 0; j < cur; ++j) {
                    tmp = sc.nextInt();
                    depth[i] = Math.max(depth[i], depth[tmp] + 1);
                    scheme[i].add(tmp);
                }
                for (int j = 0; j < (1 << cur); ++j) {
                    trtbl[i][j] = sc.nextInt();
                }
            } else {
                depth[i] = 0;
                leaves.add(i);
            }
        }
        System.out.println(depth[n]);

        int ptr = leaves.size();
        int comb[] = new int[ptr];
        do {
            for (int j = 0; j < ptr; ++j) {
                result[leaves.get(j)] = comb[j];
            }
            for (int k = 0; k < nodes.size(); k++) {
                int value = 0;
                for (int j = 0; j < scheme[nodes.get(k)].size(); ++j) {
                    value = 2 * value + result[scheme[nodes.get(k)].get(j)];
                }
                result[nodes.get(k)] = trtbl[nodes.get(k)][value];
            }
            System.out.print(result[n]);

        } while (nextComb(comb));
    }
}