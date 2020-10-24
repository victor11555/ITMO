import java.io.*;
import java.util.*;



public class K {

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

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("subsets.in"));
        int n = sc.nextInt();

        int [] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i +1;
        }
        try (FileWriter writer = new FileWriter("subsets.out")) {
            for (int i = 0; i < n; i++) {



                    for (int j = i; j < n; j++) {
                        for (int k = i; k <= j; k++) {
                            String s1 = Integer.toString(arr[k]);
                            writer.write(s1 + " ");
                        }
                        writer.write(System.lineSeparator());
                    }
                }


        } catch (IOException ex) { }
    }
}