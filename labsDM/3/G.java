import java.io.*;
import java.util.*;



public class G {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("permutations.in"));
        int n = sc.nextInt(), res = 1;


        for (int i = 1; i <=n; i ++){
            res = res * i;
        }

        int[][] perm = new int[res][n];

        for (int j = 0; j < n; j++) {
            perm[0][j] = j+1;
        }

        int current = 0;

        for (int i = 1; i < res; i++) {

            for (int j = 0; j < n; j++) {
                perm[i][j] = perm[i-1][j];
            }

            for (int k = n-1; k >= 1; k--) {
                if (perm[i][k-1] < perm[i][k]) {
                    current = k-1;

                    for (int j = n-1; j > current; j--) {
                        if (perm[i][current] < perm[i][j]) {
                            int temp = perm[i][j];
                            perm[i][j] = perm[i][current];
                            perm[i][current] = temp;
                            break;
                        }
                    }

                    int tmp;
                    for (int j = current +1; j <= current + (n-1-current)/2; j++) {
                        tmp = perm[i][n + current - j];
                        perm[i][n + current - j] = perm[i][j];
                        perm[i][j] = tmp;
                    }
                    break;
                }
            }
        }



        try (FileWriter writer = new FileWriter("permutations.out")) {
                for (int i = 1; i <= res; i++){
                    for (int j = 1; j <= n; j++) {
                        String s = Integer.toString(perm[i-1][j-1]) + " ";
                        writer.write(s);
                    }
                   writer.write(System.lineSeparator());
                }
        } catch (IOException ex) {
        }
    }
}