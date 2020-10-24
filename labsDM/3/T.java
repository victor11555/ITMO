import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.math.BigInteger;

public class T {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("brackets2num2.in"));
        String str = sc.nextLine();
        int n = str.length();
        BigInteger num = BigInteger.ZERO;
        int depth = 0;
        BigInteger d[][] = new BigInteger[n + 1][n + 2];
        d[0][0] = BigInteger.ONE;
        for (int j = 1; j <= n + 1; j++) {
            d[0][j] = BigInteger.ZERO;
        }
        for (int i = 1; i <= n; i++) {
            d[i][0] = d[i - 1][1];
            for (int j = i + 1; j <= n + 1; j++) {
                d[i][j] = BigInteger.ZERO;
            }
            for (int j = 1; j <= i; j++) {
                d[i][j] = d[i - 1][j - 1].add(d[i - 1][j + 1]);
            }
        }
        StringBuilder stck = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) == '(') {
                stck.append('(');
                depth++;
                continue;
            }
            if (str.charAt(i) == ')') {
                if (depth  + 1 <= n / 2) {
                    num = num.add(d[n - i - 1][depth + 1].shiftLeft((n - i - 1 - depth - 1) / 2));
                }
                stck.deleteCharAt(stck.length() - 1);
                depth--;
                continue;
            }

            if (str.charAt(i) == '[') {
                if (stck.length()>=1) {
                    if (stck.charAt(stck.length() - 1) == '(') {
                        num = num.add(d[n - i - 1][depth - 1].shiftLeft((n - i - 1 - depth + 1) / 2));
                    }
                }
                num = num.add(d[n - i - 1][depth  + 1].shiftLeft((n - i - 1 - depth  - 1) / 2));
                stck.append('[');
                depth++;
                continue;
            }
            if ((str.charAt(i) == ']') && (depth  + 1 <= n / 2)) {
                num = num.add(d[n - i - 1][depth  + 1].shiftLeft((n - i + 1 - depth - 1) / 2));
                stck.deleteCharAt(stck.length() - 1);
                depth--;
                continue;
            }

        }

        try (FileWriter writer = new FileWriter("brackets2num2.out")) {
            writer.write(String.valueOf(num));
        } catch (IOException ex) {
        }

    }
}