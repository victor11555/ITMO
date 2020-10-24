import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class D {

    public static int[][] qSort(int[][] pairs, int l, int r) {
        if (l < r) {
            int v = pairs[(l + r) / 2][0];
            int i = l;
            int j = r;
            while (i <= j) {
                while (pairs[i][0] > v) i++;
                while (pairs[j][0] < v) j--;
                if (i >= j) break;
                int[] tmp = pairs[i];
                pairs[i] = pairs[j];
                pairs[j] = tmp;
                i++;
                j--;
            }
            qSort(pairs, l, j);
            qSort(pairs, j+1, r);
        }
        return pairs;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("sequence.in"));
        int n = sc.nextInt();
        int[][] pairs = new int[n][2];
        StringBuilder res1 = new StringBuilder();
        StringBuilder res2 = new StringBuilder();
        for (int i = 0; i < n; i++) {
            pairs[i][0] = sc.nextInt();
            pairs[i][1] = i+1;
        }
        pairs = qSort(pairs, 0, n - 1);
        int sum1 = 0, sum2 = 0, len = 0;
        for (int i = 0; i < n; i++) {
            if (sum1 < sum2) {
                sum1 += pairs[i][0];
            }
            else {
                sum2 += pairs[i][0];
                len ++;
                res2.append(pairs[i][1] + " ");
            }
        }
        try(FileWriter writer = new FileWriter("sequence.out")) {
            if (sum1 != sum2) {
                writer.write("-1");
            } else {
                writer.write(Integer.toString(len));
                writer.write("\n");
                writer.write(res2.toString());
            }
        }
    }
}