import java.io.*;
import java.util.*;



public class Q {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("num2brackets.in"));
        int n = sc.nextInt();
        long k = sc.nextLong();

        StringBuilder sb = new StringBuilder();
        int l = 2 * n;
        int depth = 0;
        long[][] d = new long[l][l+2];
        d[0][1] = 1;

        for (int i = 1; i < l; i++) {
            for (int j = 1; j <= i+1; j++) {
                d[i][j] = d[i-1][j-1] + d[i-1][j+1];
            }
        }

        for (int i = 0; i < l; i++) {
            if (d[l - i -1][depth + 2] > k) {
                sb.append('(');
                depth++;
            } else {
                k -= d[l - i -1][depth + 2];
                sb.append(')');
                depth--;
            }
        }

        try (FileWriter writer = new FileWriter("num2brackets.out")) {
            writer.write(sb.toString());
        } catch (IOException ex) { }
    }
}