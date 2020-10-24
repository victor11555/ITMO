import java.io.*;
import java.util.*;



public class C {

    private static boolean nextComb(int[] arr){
        int n = arr.length;
        for( int i = n - 1; i>=0; i--) {
            if (arr[i] < 2) {
                arr[i]++;
                return true;
            } else {
                arr[i] = 0;
            }
        }
        return false;
    }

    private static void shift(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            arr[i] = (arr[i] + 1) % 3;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("antigray.in"));
        int n = sc.nextInt();
        int [] comb = new int[n];
        try(FileWriter writer=new FileWriter("antigray.out")){
        for (int j = 0; j < Math.pow(3, n-1); j++) {
            for (int k = 0; k < 3; k++) {
                for (int i = 0; i < n; i++) {
                    String s = Integer.toString(comb[i]);
                    writer.write(s);
                }
                writer.write(System.lineSeparator());
                shift(comb);
            }
            nextComb(comb);
        }
        }catch(IOException ex){}
    }
}