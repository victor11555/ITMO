import java.io.*;
import java.util.*;



public class H {

    private static boolean nextComb(int[] arr, int n){
        int k = arr.length;
        for (int i = k - 1; i >= 0; i--){
            if (arr[i] < n - k +i +1){
                arr[i]++;
                for (int j = i + 1; j < k; j ++){
                    arr[j] = arr[j-1] + 1;
                }
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("choose.in"));
        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] comb = new int[k];
        for (int i =0; i < k; i++){
            comb[i] = i + 1;
        }

        try (FileWriter writer = new FileWriter("choose.out")) {
            do {
                for (int i = 0; i < k; i++) {
                    String s = Integer.toString(comb[i]);
                    writer.write(s + " ");
                }
                writer.write(System.lineSeparator());
            } while (nextComb(comb, n));

        } catch (IOException ex) { }
    }
}