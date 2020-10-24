import java.io.*;
import java.util.*;

public class L {

    private static List<String> S(int n, int k) {
        StringBuilder str = new StringBuilder();
        List<String> res = new ArrayList<String>();
        if (k==n) {
            for (int i=0;i<k;i++) {
                str.append(i);
            }
            res.add(str.toString());
            return res;
        }
        if (k==1) {
            for (int i=1;i<=n;i++) {
                str.append(0);
            }
            res.add(str.toString());
            return res;
        }
        List<String> L = S(n-1,k);
        for (String el:L) {
            for (int i = 0; i < k; i++) {
                res.add(el + i);
            }
        }
        L = S(n-1,k-1);
        for (String el:L) {
            res.add(el + String.valueOf(k-1));
        }
        return res;
    }


    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("part2sets.in"));
        int n = sc.nextInt();
        int k = sc.nextInt();
        List<String> res1 = S(n,k);

        try(FileWriter writer=new FileWriter("part2sets.out")) {
            for (String el : res1) {
                String[] res2 = new String[k];
                for (int i = 0; i < k; i++) {
                    res2[i] = "";
                }
                for (int i = 0; i < n; i++) {
                    res2[Character.getNumericValue(el.charAt(i))] += String.valueOf(i + 1) + " ";
                }

                for (int i = 0; i < k; i++) {
                    writer.write(res2[i]);
                    writer.write(System.lineSeparator());
                }
                writer.write(System.lineSeparator());
            }
        }catch(IOException ex){}
    }
}