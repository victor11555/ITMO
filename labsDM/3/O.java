import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class O{

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("num2choose.in"));
        int n = sc.nextInt();
        int k = sc.nextInt();
        BigInteger m = sc.nextBigInteger();
        StringBuilder choose = new StringBuilder();
        Integer next = 1;
        BigInteger[] factorial = new BigInteger[n + 1];
        factorial[1] = BigInteger.ONE;
        factorial[0] = BigInteger.ONE;
        for (Integer i = 2; i < n; i++) {
            factorial[i] = factorial[i - 1].multiply(BigInteger.valueOf(i));
        }

        while (k > 0) {
            BigInteger tmp = factorial[n - 1].divide(factorial[k - 1].multiply(factorial[n-k]));
            if (m.compareTo(tmp) < 0) {
                choose.append(next.toString() + " ");
                k --;
            } else {
                 m = m.subtract(tmp);
            }
            n --;
            next ++;
        }

        try (FileWriter writer = new FileWriter("num2choose.out")) {
                writer.write(choose.toString() + " ");
        } catch (IOException ex) { }
    }
}