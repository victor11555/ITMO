import java.lang.Math;
import java.util.Scanner;

public class D {
    public static void main(String[] args) {
        long s;
        int n;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        long[]  a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();;
        }
        s = sc.nextLong();
        long OK = 0;
        String result = new String();
        boolean  flag1, flag2, needOR = false;
        long x = (long)(Math.pow(2, 33));
        long tmp;
        if (s == 0) {
            System.out.println ("1&~1");
        } else {
            for (int i = 0; i < 33; i++) {
                if ((s & (long)Math.pow(2, i)) == 0) {
                    flag1 = false;
                } else {
                    flag1 = true;
                }
                if (flag1 == true) {
                    if (needOR == true) {
                        result += "|";
                    }
                    needOR = true;
                    boolean needAND = false;
                    tmp = x - 1;
                    for (int j = 0; j < n; j++) {
                        if ((a[j] & (long)Math.pow(2, i)) == 0) {
                            flag2 = false;
                        } else {
                            flag2 = true;
                        }
                        if (needAND == true) {
                            result += "&";
                        }
                        needAND = true;
                        if (flag2 == false) {
                            result += "~" + Integer.toString(j + 1);
                            tmp = tmp & ~a[j];
                        } else {
                            result += Integer.toString(j + 1);
                            tmp = tmp & a[j];
                        }
                    }
                    OK= OK | tmp;
                }
            }
            if (OK == s) {
                System.out.println(result);
            } else {
                System.out.println("Impossible");
            }
        }
    }
}