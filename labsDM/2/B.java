import java.io.*;
import java.util.*;

public class B {
    public static void main(String[] args) throws FileNotFoundException{
        String s;

         Scanner sc = new Scanner(new File("bwt.in"));
         s = sc.nextLine();
        String[] str = new String[s.length()];

        int n = s.length();
        str[0] = s;
        for (int i = 1; i < n; i++) {
            str[i] =  str[i - 1].substring(1, n) + str[i-1].charAt(0);
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                 String x = str[i];
                 String x2 = str[j];

                if (! (x.compareTo(x2) <= 0)) {
                    String tmp = str[j];
                    str[j] =  str[i];
                    str[i]= tmp;
                }
            }
        }
        try(FileWriter writer = new FileWriter("bwt.out")) {
            for (int i = 0; i < n; i++) {
                writer.write(str[i].charAt(n-1));
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}