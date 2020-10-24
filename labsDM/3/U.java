import java.io.*;
import java.util.*;



public class U {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("num2part.in"));
        Integer n = sc.nextInt();
        Integer r = sc.nextInt();

        int last = 1;

        int[][] d = new int[n+2][n+2];
        List<Integer> arr = new ArrayList<Integer>();
        for (int i = 1; i <= n+1; i++) {
            d[i][i] = 1;
            for (int j = i-1; j >=1; j--) {
                d[i][j] = d[i][j+1] + d[i - j][j];
            }
        }
        int sum = n;
        while (sum > 0) {
            for (int i = last; i < n+1; i++) {
                if (r < d[sum][last] - d[sum][i+1]) {
                    arr.add(i);
                    r -= (d[sum][last] - d[sum][i]);
                    last = i;
                    sum -= i;
                    break;
                }
            }
        }
        String res = "";
        for (Integer el:arr) {
            res += el.toString() + "+";
        }
        res = res.substring(0,res.length()-1);
        try (FileWriter writer = new FileWriter("num2part.out")) {
            writer.write(res);
        } catch (IOException ex) { }
    }
}