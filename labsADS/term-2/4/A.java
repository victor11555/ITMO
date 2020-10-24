import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class A {

    public static int[] qSort(int[] arr, int l, int r) {
        if (l < r) {
            int v = arr[(l + r) / 2];
            int i = l;
            int j = r;
            while (i <= j) {
                while (arr[i] < v)
                    i++;
                while (arr[j] > v)
                    j--;
                if (i >= j) break;
                int x = i++;
                int y = j--;
                int tmp = arr[x];
                arr[x] = arr[y];
                arr[y] = tmp;
            }
            qSort(arr, l, j);
            qSort(arr, j+1, r);
        }
        return arr;
    }

    public static int greedy(int[] arr, int n, int k) {
        int res = 0, i = 0;
        while (k > 0 && i < n) {
            if (arr[i] <= k) {
                k -= arr[i];
                res++;
            }
            i++;
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("cobbler.in"));
        int k = sc.nextInt();
        int n = sc.nextInt();
        int[] arr = new int[501];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        arr = qSort(arr, 0, n - 1);
        try(FileWriter writer = new FileWriter("cobbler.out")) {
            writer.write(Integer.toString(greedy(arr, n, k)));
        }
    }
}