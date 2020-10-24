import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class S {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("num2brackets2.in"));
        int n = sc.nextInt();
        BigInteger k = sc.nextBigInteger();;
        String res = "";
        StringBuilder str = new StringBuilder();
        int depth = 0;

        BigInteger d[][] = new BigInteger [2*n+1][2*n+2];
        d[0][0] = BigInteger.ONE;
        for (int j = 1; j <= 2*n+1; j++) {
            d[0][j] = BigInteger.ZERO;
        }
        for (int i = 1; i <= 2*n; i++) {
            d[i][0] = d[i-1][1];
            for (int j = i+1; j <= 2*n+1; j++) {
                d[i][j] = BigInteger.ZERO;
            }
            for (int j = 1; j <= i; j++) {
                d[i][j] = d[i-1][j-1].add(d[i-1][j+1]);
            }
        }


        for (int i = 0; i < 2*n; i++) {
            BigInteger cntr  = BigInteger.ZERO;

            if (depth + 1 <= n) {
                cntr = d[2*n - i - 1][depth + 1].shiftLeft((2*n - i - 1 - depth - 1) / 2);
            } else cntr = BigInteger.ZERO;
            if (cntr.compareTo(k)>0) {
                res += '(';
                str.append('(');
                depth++;
                continue;
            }
            k = k.subtract(cntr);


            if ((str.length() > 0) && (str.charAt(str.length() - 1) == '(') && (depth-1 >= 0)) {
                cntr = d[2 * n - i - 1][depth - 1].shiftLeft((2 * n - i - 1 - depth + 1) / 2);
            } else cntr = BigInteger.ZERO;
            if (cntr.compareTo(k)>0) {
                res += ')';
                if (str.length() > 1) {
                    str.deleteCharAt(str.length() - 1);
                }
                depth--;
                continue;
            }
            k = k.subtract(cntr);


            if (depth + 1 <= n) {
                cntr = d[2 * n - i - 1][depth + 1].shiftLeft((2 * n - i - 1 - depth - 1) / 2);
            } else cntr = BigInteger.ZERO;
            if (cntr.compareTo(k)>0) {
                res += '[';
                str.append('[');
                depth++;
                continue;
            }
            k = k.subtract(cntr);


            res += ']';
            if (str.length() > 1) {
                str.deleteCharAt(str.length() - 1);
            }
            depth--;
        }
        try(FileWriter writer=new FileWriter("num2brackets2.out")){
            writer.write(res);
        }catch(IOException ex){}
    }
}