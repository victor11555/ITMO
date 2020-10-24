import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;


public class C {

    private static  boolean isT0(String f, int si) {
        boolean T0 = false;
        if (f.charAt(0) == '0') {
            T0 = true;
        }
        return T0;
    }

    private static  boolean isT1(String  f, int si) {
        boolean T1 = false;
        int n = (int) Math.pow(2, si);
        if (f.charAt(n - 1) == '1') {
            T1 = true;
        }
        return T1;
    }

    private static   boolean isM(String f, int si) {
        int n = 1 << si;
        boolean M = true;
        for (int i = 0; i < n; i++) {
            for (int k = i; k < n; k++) {
                if ((i & k) == i && f.charAt(i) > f.charAt(k)) {
                    M = false;
                }
            }
        }
        return M;
    }

    private static  boolean isD(String f, int si) {
        boolean D = true;
        int l = 0;
        int r = (int) Math.pow(2, si) - 1;
        if (f.length() == 1) {
            D = false;
        }
        while (l < r) {
            if (f.charAt(l) == f.charAt(r)) {
                D = false;
            }
            l++;
            r--;
        }
        return D;
    }

    private static  boolean isL(String f, int si){
        boolean L = true;
        int n = 1 << si;
        int[] a = new int[n];
        int[] kof = new int[n];
        int[] count1 = new int[n];

        for (int i = 0; i < n; i++) {
            int z = i;
            count1[i] = 0;
            while (z > 0) {
                count1[i] += z % 2;
                z /= 2;
            }
        }
        for (int k = 0; k < n; k++) {
            a[k] =  f.charAt(k) - '0';
        }
        kof[0] = a[0];
        int j = 1;
        int k = n - 1;
        while (k > 0) {
            for (int i = 0; i < k; i++) {
                a[i] = (a[i] + a[i + 1]) % 2;
            }
            kof[j] = a[0];
            j++;
            k--;
        }
        for (int i = 0; i < n; i++) {
            if (kof[i] == 1 && count1[i] > 1) {
                L = false;
            }
        }
        return L;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n, si;
        n = sc.nextInt();
        String f;
        boolean T0 = true;
        boolean T1 = true;
        boolean D = true;
        boolean M = true;
        boolean L = true;

        for (int i = 0; i < n; i++) {
            si = sc.nextInt();
            f = sc.next();
            if (si == 0) {
                D = false;
                if (f.charAt(0) == '0') {
                    T1 = false;
                } else {
                    T0 = false;
                }
            } else {
                if (!isT0(f, si)) T0 = false;
                if (!isT1(f, si)) T1 = false;
                if (!isM(f, si)) M = false;
                if (!isD(f, si)) D = false;
                if (!isL(f, si)) L = false;
            }
        }
        if (!M && !D && !T0 && !T1 && !L) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}