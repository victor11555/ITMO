import java.io.*;
import java.util.*;



public class W {

    private static boolean nextComb(Integer[] arr){
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
    private static boolean prevComb(Integer[] arr){
        int n = arr.length;
        for( int i = n - 1; i>=0; i--) {
            if (arr[i] == 1) {
                arr[i]--;
                return true;
            } else {
                arr[i] = 1;
            }
        }
        return false;
    }


    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("nextvector.in"));
        String str = sc.nextLine();
        int n = str.length();
        Integer [] comb = new Integer[n];
        Integer [] comb1 = new Integer[n];

        for (int i = 0; i <n; i++) {
            comb[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
            comb1[i] = comb[i];
        }

        try(FileWriter writer=new FileWriter("nextvector.out")){

        if (prevComb(comb)) {
            for (int i = 0; i < n; i++) {
                writer.write(comb[i].toString());
            }
        } else writer.write("-");


        if (nextComb(comb1)) {
            for (int i = 0; i < n; i++) {
                writer.write(comb1[i].toString());
            }
        } else writer.write("-");

        }catch(IOException ex){}
    }
}