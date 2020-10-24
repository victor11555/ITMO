import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.lang.*;


public class E {

    public static int k, r;
    public static int[][] matrix;

    static int[] multi(int v, int l, int r, int a, int b){

        if(l >= a && r <= b){
            return matrix[v];
        }

        if(l >= b || r <= a){
            int[] ed = new int[4];
            ed[0] = 1;
            ed[1] = 0;
            ed[2] = 0;
            ed[3] = 1;
            return ed;
        }
        int m = (l+r)/2;
        return reboot(multi(v*2 + 1, l, m, a, b), multi(v*2 + 2, m, r, a, b));
    }

    static int[] reboot(int[] ar, int[] br){
        int[] array = new int[4];
        array[0] = ((ar[0] * br[0]) + (ar[1] * br[2]))%r;
        array[1] = ((ar[0] * br[1]) + (ar[1] * br[3]))%r;
        array[2] = ((ar[2] * br[0]) + (ar[3] * br[2]))%r;
        array[3] = ((ar[2] * br[1]) + (ar[3] * br[3]))%r;
        return array;
    }

    public static void main(String[] args) throws FileNotFoundException{

        Scanner sc = new Scanner(new File("crypto.in"));
        r = sc.nextInt();
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][]results = new int[m][2];
        int a,b;

        k = 1;
        while(k < n){
            k *= 2;
        }

        matrix = new int[2*k - 1][4];
        for(int i = 0; i < n; i++) {
            matrix[k - 1 + i][0] = sc.nextInt();
            matrix[k - 1 + i][1] = sc.nextInt();
            matrix[k - 1 + i][2] = sc.nextInt();
            matrix[k - 1 + i][3] = sc.nextInt();
        }
        for(int i = n; i < k - 1; i++) {
            matrix[k - 1 + i][0] = 1;
            matrix[k - 1 + i][1] = 0;
            matrix[k - 1 + i][2] = 0;
            matrix[k - 1 + i][3] = 1;
        }
        for(int i = k - 2; i >= 0; i--){
            matrix[i]= reboot(matrix[2*i+1], matrix[2*i+2]);
        }


        for (int i = 0; i < m; i++){
            a = sc.nextInt();
            b = sc.nextInt();
            results[i]= multi(0,0,k,a - 1, b);
        }


        try(FileWriter writer = new FileWriter("crypto.out")) {
            for (int i = 0; i < m; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(Integer.toString(results[i][0]));
                sb.append(" ");
                sb.append(Integer.toString(results[i][1]));
                sb.append("\n");
                sb.append(Integer.toString(results[i][2]));
                sb.append(" ");
                sb.append(Integer.toString(results[i][3]));
                sb.append("\n");
                String str1 = sb.toString();
                writer.write(str1);
                writer.write(System.lineSeparator());
            }

        }catch(IOException ex){}

    }
}
