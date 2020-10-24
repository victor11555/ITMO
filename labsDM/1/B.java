import java.util.Scanner;

public class B {

    static int[][] array;
    static int[] tmp;
    static Scanner sc;
    static int n, k, flag, i, g, j, x, l, razmer, cur, j7;
    
    public static void check() {
        j7 = 0;
        for (i = 0; i < razmer; i++) {
            l = 0;
            for(j = 0; j < n; j++) {
                if (array[i][j] == -1) {
                    l++;
                } else {
                    j7 = j;
                }
            }
            if (l == n - 1) {
                cur = array[i][j7];
                flag = 0;
                break;
            } else {
                flag = -1;
            }
        }
    }

    public static void change() {
        for (i = 0; i < razmer; i++) {
            if (array[i][j7] == cur ) {
                for (j = 0; j < n; j++) {
                    array[i][j] = -1;
                }
            } else {
                if (array[i][j7] != -1) {
                    l = 0;
                    for (j = 0; j < n; j++) {
                        if (array[i][j] == -1) {
                            l++;                
                        }
                    }
                    if (l == n - 1) { 
                        flag = 1;
                        break;
                    }
                }
            }
        }  
    }


    public static void main(String[] args) {
        sc = new Scanner(System.in);
        n = sc.nextInt();
        k = sc.nextInt();
        array = new int[k][n];
        tmp = new int[n];
        razmer = 0;
        flag = 0;
        g = 0;
        x = 0;
        l = 0;
        for (i = 0; i < k; i++) {
            flag = 1;
            x = 0;
            for (j = 0; j < n; j++) {
                tmp[j] = sc.nextInt();
                if (tmp[j] == -1) {
                    x++;
                }
            }
            if (x == n) {
                flag = 0;
            }
            if (flag == 1) {
                for (g = 0; g < razmer; g++) {
                    l = 0;
                    for (j = 0; j < n; j++){
                        if (array[g][j] != tmp[j]){
                            flag = 1;
                            break;
                        } else {
                            flag = 0;
                            l++;
                        }
                    }
                    if (l == n) {
                        flag = 0;
                        break;
                    }
                }
            }
            if (flag == 1) {
                for (j = 0; j < n; j++){
                    array[g][j] = tmp[j];
                }
                razmer +=  1;
            }
        }
        if (razmer != 0){
            while (true) {
                check();
                if (flag == -1) break;
                change();
                if (flag == 1) break;
                for (i = 0; i < k; i++) {
                    array[i][j7] = -1;
                }
            }
            if (flag == -1) {
                System.out.println("NO");
            }
            if (flag == 1) {
                System.out.println("YES");
            }
        } else {
            System.out.println("NO");
        }
    }
}