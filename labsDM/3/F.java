import java.io.*;
import java.util.*;



public class F {

    private static boolean nextComb(int[] arr) {
        int n = arr.length;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] < 1) {
                arr[i]++;
                return true;
            } else {
                arr[i] = 0;
            }
        }
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("vectors.in"));
        Integer cnt = 0;
        int n = sc.nextInt();
        StringBuilder sb = new StringBuilder();

        int[] comb = new int[n];

        do {
        boolean flag = true;

        for(int j = 0; j < n - 1; j++){
            if (comb[j] == 1 && comb[j+1] == 1){
                flag = false;
            }
        }

        if(flag) {
            for (int i = 0; i < n; i++) {
                sb.append(comb[i]);
            }
            cnt ++;
            sb.append("\n");
        }
        } while (nextComb(comb));


        try (FileWriter writer = new FileWriter("vectors.out")) {
            writer.write(cnt.toString());
            writer.write(System.lineSeparator());
            writer.write(sb.toString());
        } catch (IOException ex) {
        }
    }
}