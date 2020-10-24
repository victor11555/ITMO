import java.io.*;
import java.util.*;



public class N{

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("perm2num.in"));
        int n = sc.nextInt();
        Long res = 0L;
        int[] arr = new int[n+1];
        int[] a = new int[n+1];
        long[] factorial = new long[n+1];
        factorial[1] = 1;
        for (int i = 2; i < n; i++) {
            factorial[i] = factorial[i-1] * i;
        }
        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= a[i] - 1; j++ ){
                if (arr[j] == 0) {
                    res += factorial[n - i];
                }
            }
            arr[a[i]] = 1;
        }
        try (FileWriter writer = new FileWriter("perm2num.out")) {
            writer.write(res.toString());
        } catch (IOException ex) { }
    }
}