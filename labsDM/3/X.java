import java.io.*;
import java.util.*;



public class X {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("nextperm.in"));
        int n = sc.nextInt();

        int[][] perm = new int[3][n];

        for (int i = 0; i < n; i++) {
                perm[2][i] = sc.nextInt();
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                perm[i][j] = perm[2][j];
            }
        }


        int current = 0;

        for (int k = n-1; k >= 1; k--) {
            if (perm[1][k-1] < perm[1][k]) {
                current = k-1;
                for (int j = n-1; j > current; j--) {
                    if (perm[1][current] < perm[1][j]) {
                        int temp = perm[1][j];
                        perm[1][j] = perm[1][current];
                        perm[1][current] = temp;
                        break;
                    }
                }

                int tmp;
                for (int j = current + 1; j <= current + (n-1-current)/2; j++) {
                    tmp = perm[1][n + current - j];
                    perm[1][n + current - j] = perm[1][j];
                    perm[1][j] = tmp;
                }
                break;
            }
        }


        for (int k = n-1; k >= 1; k--) {
            if (perm[0][k-1] > perm[0][k]) {
                current = k-1;
                for (int j = n-1; j > current; j--) {
                    if (perm[0][current] > perm[0][j]) {
                        int temp = perm[0][j];
                        perm[0][j] = perm[0][current];
                        perm[0][current] = temp;
                        break;
                    }
                }

                int tmp;
                for (int j = current + 1; j <= current + (n-1-current)/2; j++) {
                    tmp = perm[0][n + current - j];
                    perm[0][n + current - j] = perm[0][j];
                    perm[0][j] = tmp;
                }
                break;
            }
        }
        int cnt1 = 0, cnt0 = 0;

        for (int j = 0; j < n; j++) {
            if(perm[1][j] == perm[2][j]){
                cnt1++;
            }
            if(perm[0][j] == perm[2][j]){
                cnt0++;
            }
        }

        if (cnt0 == n){
            for (int j = 0; j < n; j++) {
                perm[0][j] = 0;
            }
        }
        if (cnt1 == n){
            for (int j = 0; j < n; j++) {
                perm[1][j] = 0;
            }
        }


        try (FileWriter writer = new FileWriter("nextperm.out")) {
                for (int i = 0; i < 2; i++){
                    for (int j = 1; j <= n; j++) {
                        String s = Integer.toString(perm[i][j-1]) + " ";
                        writer.write(s);
                    }
                   writer.write(System.lineSeparator());
                }
        } catch (IOException ex) {
        }
    }
}