import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class M{

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("num2perm.in"));
        Integer n = sc.nextInt();
        BigInteger k = sc.nextBigInteger();
        StringBuilder permutation = new StringBuilder();
        int[] arr = new int[n+1];
        BigInteger[] factorial = new BigInteger[n + 1];
        factorial[1] = BigInteger.ONE;
        factorial[0] = BigInteger.ONE;

        for (Integer i = 2; i < n; i++) {
            factorial[i] = factorial[i - 1].multiply(BigInteger.valueOf(i));
        }


        for (Integer i = 1; i <= n; i++) {
            BigInteger tmp = factorial[n-i];
            BigInteger flag = k.divide(tmp);
            k = k.mod(tmp);
            BigInteger curr = BigInteger.ZERO;
            for (Integer j = 1; j <= n; j++){
                if (arr[j] == 0) {
                    curr = curr.add(BigInteger.ONE) ;
                    if (curr.compareTo(flag.add(BigInteger.ONE)) == 0) {
                        permutation.append(j.toString() + " ");
                        arr[j] = 1;
                    }
                }
            }
        }

        try (FileWriter writer = new FileWriter("num2perm.out")) {
            writer.write(permutation.toString());
        } catch (IOException ex) { }
    }
}