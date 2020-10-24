import java.io.*;
import java.util.*;
import java.lang.*;

public class B {

    public static long[] t = new long[1];
    public static int k;

    static long rsq (int v, int l, int r, int a, int b) {
        if(l >= a && r <= b){
            return t[v];
        }
        if(l >= b || r <= a){
            return 0L;
        }
        return rsq(v*2 + 1, l, (l+r)/2, a, b) + rsq (v*2 + 2, (l+r)/2, r, a, b);
    }

    static void update (int i, long x) {
        t[k - 1 + i] = x;
        int j = k - 1 + i;
        while (j > 0){
            j = (j - 1)/ 2;
            t[j] = t[2*j + 1] + t[2*j + 2];
        }
    }


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long[] arr = new long[n];
        ArrayList<Long> results= new ArrayList<>();

        for (int i = 0; i < n; i++){
            arr[i] = sc.nextLong();
        }


        k = 1;
        while(k < n){
            k *= 2;
        }


        t = new long[2*k - 1];
        for(int i = 0; i < n; i++) {
            t[k - 1 + i] = arr[i];
        }
        for(int i = n; i < k - 1; i++) {
            t[k - 1 + i] = 0;
        }
        for(int i = k - 2; i >= 0; i--){
            t[i] = t[2*i + 1] + t[2*i + 2];
        }

        while(sc.hasNext()){
            String str = sc.next();
            if(str.equals("set")){
                int i = sc.nextInt();
                long x = sc.nextInt();
                update(i-1, x);
            }else{
                int a = sc.nextInt();
                int b = sc.nextInt();
                long tmp = rsq(0, 0, k, a-1, b);
                results.add(tmp);
            }
        }
        for (int i = 0; i < results.size(); i++){
            System.out.println(results.get(i));
        }
    }
}
