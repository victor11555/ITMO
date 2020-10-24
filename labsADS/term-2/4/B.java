import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class B {
    public static int[][] qSort(int[][] pairs, int l, int r) {
        if (l < r) {
            int v = pairs[(l + r) / 2][1];
            int i = l;
            int j = r;
            while (i <= j) {
                while (pairs[i][1] < v)
                    i++;
                while (pairs[j][1] > v)
                    j--;
                if (i >= j) break;
                int x = i++;
                int y = j--;
                int[] tmp = pairs[x];
                pairs[x] = pairs[y];
                pairs[y] = tmp;
            }
            qSort(pairs, l, j);
            qSort(pairs, j+1, r);
        }
        return pairs;
    }

    public static int greedy(int[][] pairs, int n, int k) {
        int res = 1, end = pairs[0][1];
        if(end > k){
            return 0;
        }
        for (int i = 1; i < n; i++) {
            if (pairs[i][0] >= end) {
                end = pairs[i][1];
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("request.in"));
        int n = sc.nextInt();
        int[][] pairs = new int[n][2];
        for (int i = 0; i < n; i++) {
            pairs[i][0] = sc.nextInt();
            pairs[i][1] = sc.nextInt();
        }
        pairs = qSort(pairs, 0, n - 1);
        try(FileWriter writer = new FileWriter("request.out")) {
            writer.write(Integer.toString(greedy(pairs, n, 1440)));
        }
    }
}