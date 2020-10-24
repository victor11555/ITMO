import java.util.Scanner;

public class A{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] arr = new int[2 * m];
        int i, x = 0, g = 0, f = 0, y = 0, z = 0;

        for (i = 0; i < 2 * m;) {
            x = sc.nextInt();
            y = sc.nextInt();
            f= 1;
            for (g = 0; g < z;) {
                if (arr[g] == x && arr[g + 1] == y) {
                    f = 0;
                }
                g += 2;
            }
            if (f == 1) {
                arr[g++] = x;
                arr[g] = y;
                z += 2;
            }
            i += 2;
        }
        int[] arr1 = new int[n+1];
        arr1[1] = -1;
        i = 1;
        int j = 1;
        f = 0;
        while (i <= n){
            if (arr1[i] == 0) {
                arr1[i] = 1;
                i = 1;
            } else {
                if (arr1[i] == 1) {
                    arr1[i] = 0;
                    i++;
                } else {
                    arr1[i] = 0;
                    i = 1;
                }
            }
            f = 0;
            for (j = 0; j < 2 * m;) {
                int a = arr[j++];
                int b = arr[j++];
                if (a == 0) {
                    break;
                }
                if (a < 0){
                    if ( arr1[Math.abs(a)] == 0) {
                        a = 1;
                    } else {
                        a = 0;
                    }
                } else {
                    a = arr1[a];
                }
                if (b < 0){
                    if ( arr1[Math.abs(b)] == 0) {
                        b = 1;
                    } else {
                        b = 0;
                    }
                } else {
                    b = arr1[b];
                }
                if ((a + b) == 0) {
                    f = 1;
                    break;
                }

            }
            if(f == 0) {
                System.out.println("NO");
                break;
            }

        }
        if(f == 1) {
            System.out.println("YES");
        }
    }
}