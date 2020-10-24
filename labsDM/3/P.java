import java.io.*;
import java.util.*;
import java.math.BigInteger;


public class P{

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("choose2num.in"));
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] choose = new int[k + 1];
        BigInteger[] factorial = new BigInteger[n+1];
        BigInteger numOfChoose = BigInteger.valueOf(0);
        factorial[0] = BigInteger.valueOf(1);
        for (int i = 1; i <= n; i++)
            factorial[i] = factorial[i - 1].multiply(BigInteger.valueOf(i));

        for (int i = 1; i <= k ; i++) {
            choose[i] = sc.nextInt();
        }

        for (int i = 1; i <= k ; i++) {
            if (choose[i - 1] + 1 <= choose[i] - 1) {
                for (int j = choose[i - 1] + 1; j <= choose[i] - 1; j++) {
                    numOfChoose = numOfChoose.add(factorial[n - j].divide(factorial[k - i].multiply(factorial[n - j - k + i])));
                }
            }
        }
        try (FileWriter writer = new FileWriter("choose2num.out")) {
            writer.write(numOfChoose.toString());
        } catch (IOException ex) { }
    }
}