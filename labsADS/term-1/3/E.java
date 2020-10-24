import java.io.*;
import java.util.*;
import java.lang.*;

public class E {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(System.in);
        String str1 = sc.nextLine();
        String str2 = sc.nextLine();


        int[][] d = new int[str1.length()+1][str2.length()+1];
        d[0][0] = 0;

        for (int i = 0; i < str2.length()+1; i++){
            d[0][i] = i;
        }
        for (int i = 0; i < str1.length()+1; i++) {
            d[i][0] = i;
        }

        for (int i = 1; i < str1.length()+1; i++){
            for (int j = 1; j < str2.length()+1; j++) {
                if(str1.charAt(i-1) != str2.charAt(j-1)){
                    d[i][j] = Math.min(d[i][j-1] + 1, Math.min(d[i-1][j] + 1, d[i-1][j-1]+ 1));
                }else {
                    d[i][j] = Math.min(d[i][j-1] + 1, Math.min(d[i-1][j] + 1, d[i-1][j-1]));;
                }
            }
        }
        System.out.println(Integer.toString(d[str1.length()][str2.length()]));
    }
}