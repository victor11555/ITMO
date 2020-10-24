import java.io.*;
import java.util.*;

public class A {


    public static boolean check(String str) {



        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("problem1.in"));
        String str = sc.next();
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        int[] set = new int[k];
        for (int i = 0; i < k; i++) {
           set[i] = sc.nextInt();
        }

        String[][] array = new String[n][n];
        for (int i = 0; i < m; i++) {
            int a =  sc.nextInt();
            int b = sc.nextInt();
            array[a][b] = sc.next();
        }



        try(FileWriter writer=new FileWriter("problem1.out")){
            String s;
            if (check(str)) s = "Accepts";
            else s = "Rejects";
            writer.write(s);
        }catch(IOException ignored){}
    }
}