import java.util.ArrayList;
import java.util.Scanner;

public class C {

    public static long[] minarr;
    public static long[] setarr;
    public static long[] addarr;
    public static int k;

    static void setpush(int v, int l, int r){
        if (setarr[v] == Long.MAX_VALUE) {
            return;
        }
        if (r - l > 1) {
            minarr[2*v+1] = setarr[v];
            minarr[2*v+2] = setarr[v];
            setarr[2*v+1] = setarr[v];
            setarr[2*v+2] = setarr[v];
            addarr[2*v+1] = 0;
            addarr[2*v+2] = 0;
        }
        setarr[v] = Long.MAX_VALUE;
    }

    static void addpush(int v, int l, int r){
        if (addarr[v] == 0) {
            return;
        }
        if (r - l > 1) {
            minarr[2*v+1] += addarr[v];
            addarr[2*v+1] += addarr[v];
            minarr[2*v+2] += addarr[v];
            addarr[2*v+2] += addarr[v];
        }
        addarr[v] = 0;
    }

    static void setupdate (int v, int l, int r, int x, int a, int b) {
        setpush(v, l, r);
        addpush(v, l, r);
        if(a >= r || l >= b){return;}
        if(l >= a && r <= b){
            setarr[v] = x;
            minarr[v] = x;
            return;
        }
        int m = (l + r)/2;
        setupdate(2*v+1, l, m,  x, a, b);
        setupdate(2*v+2, m, r,  x, a, b);
        minarr[v] = Math.min(minarr[2*v + 1], minarr[2*v + 2]);
    }

    static void addupdate (int v, int l, int r, int x, int a, int b) {
        setpush(v, l, r);
        addpush(v, l, r);
        if(a >= r || l >= b){return;}
        if(l >= a && r <= b){
            addarr[v] += x;
            minarr[v] += x;
            return;
        }
        int m = (l + r)/2;
        addupdate(2*v+1, l, m,  x, a, b);
        addupdate(2*v+2, m, r,  x, a, b);
        minarr[v] = Math.min(minarr[2 * v + 1], minarr[2 * v + 2]);
    }



    static long min (int v, int l, int r, int a, int b) {
        setpush(v, l, r);
        addpush(v, l, r);
        if(a >= r || l >= b){return Long.MAX_VALUE;}
        if(l >= a && r <= b){ return minarr[v];}
        int m = (l + r)/2;
        long sl = min(2*v+1, l, m, a, b);
        long sr = min(2*v+2, m, r, a, b);
        return Math.min(sl, sr);
    }


    public static void main(String[] args){
        int a, b, x;
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        ArrayList<Long> results= new ArrayList<>();

        k = 1;
        while(k < n){
            k *= 2;
        }
        minarr = new long[2*k - 1];
        setarr = new long[2*k - 1];
        addarr = new long[2*k - 1];
        for (int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }
        for(int i = 0; i < 2*k - 1 ; i++) {
            setarr[i] = Long.MAX_VALUE;
        }
        for(int i = 0; i < n; i++) {
            minarr[k - 1 + i] = arr[i];
        }
        for(int i = n; i < k; i++) {
            minarr[k - 1 + i] = Long.MAX_VALUE;
        }
        for(int i = k - 2; i >= 0; i--){
            minarr[i] = Math.min(minarr[2*i + 1], minarr[2*i + 2]);
        }
        while(sc.hasNext()){
            String str = sc.next();
            if(str.equals("set")){
                a = sc.nextInt();
                b = sc.nextInt();
                x = sc.nextInt();
                setupdate(0, 0, k, x, a - 1, b);
            }else if(str.equals("add")) {
                a = sc.nextInt();
                b = sc.nextInt();
                x = sc.nextInt();
                addupdate(0, 0, k, x, a - 1, b);
            }else{
                a = sc.nextInt();
                b = sc.nextInt();
                long tmp = min(0, 0, k, a - 1, b);
                results.add(tmp);
            }
        }

        for (int i = 0; i < results.size(); i++){
            System.out.println(results.get(i));
        }

    }
}
