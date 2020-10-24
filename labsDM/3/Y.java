import java.io.*;
import java.util.*;



public class Y {

    private static boolean nextComb(Integer[] arr, int n){
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

        Scanner sc = new Scanner(new File("nextchoose.in"));
        int n = sc.nextInt();
        int k = sc.nextInt();
        Integer comb[] = new Integer[k];

        for (int i = 0; i < k; i++) {
            comb[i] = sc.nextInt();
        }

        try(FileWriter writer=new FileWriter("nextchoose.out")){

            if (nextComb(comb, n)) {
                for (int i = 0; i < k; i++) {
                    writer.write(comb[i].toString() + " ");
                }
            } else writer.write("-1");

        }catch(IOException ex){}
    }
}