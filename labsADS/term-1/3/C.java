import java.util.ArrayList;
import java.util.Scanner;

public class C {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        int[] d = new int[n];
        ArrayList<Integer> res = new ArrayList();

        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();;
        }

        for (int i = 0; i < n; i++) {
            d[i]=1;
            for (int j = 0; j < i; j++){
                if(a[j] < a[i]){
                    if( d[j] + 1 > d[i]) {
                        d[i] = d[j] + 1;
                    }
                }
            }
        }

        int k = d[0];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if(d[i] > k){
                k = d[i];
                j = i;
            }
        }
        res.add(a[j]);
        int tmp = k;
        for (int i = j; i >= 0; i--) {
            if (tmp - d[i] == 1  &&  a[i] < a[j]){
                res.add(0, a[i]);
                tmp--;
                j = i;
            }
        }

        System.out.println(k);
        for (int i = 0; i < k; i++) {
            System.out.print(res.get(i) + " ");
        }
    }
}