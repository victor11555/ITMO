import java.io.*;
import java.util.*;



public class A {
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

        Scanner sc = new Scanner(new File("allvectors.in"));
        int n = sc.nextInt();

        int [] comb = new int[n];
        try(FileWriter writer=new FileWriter("allvectors.out")){
            do {
                for (int i = 0; i <n; i++) {
                    String s = Integer.toString(comb[i]);
                    writer.write(s);
                }
                writer.write(System.lineSeparator());
            } while (nextComb(comb));
        }catch(IOException ex){}
    }
}