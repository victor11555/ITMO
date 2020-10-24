import java.io.*;
import java.util.*;



public class R {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("brackets2num.in"));

        String str = "";

        while(sc.hasNextLine()) {
            str += sc.nextLine();
        }

        int n = str.length();
        Long num = 0L;
        int depth = 0;
        long[][] d = new long[n][n+2];
        d[0][1] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= i+1; j++) {
                d[i][j] = d[i-1][j-1] + d[i-1][j+1];
            }
        }
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) == '(') {
                depth++;
            } else {
                num += d[n - i - 1][depth + 2];
                depth--;
            }
        }

        try (FileWriter writer = new FileWriter("brackets2num.out")) {
            writer.write(num.toString());
        } catch (IOException ex) { }
    }
}