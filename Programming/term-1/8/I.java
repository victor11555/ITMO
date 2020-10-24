import java.io.*;
import java.util.*;
public class I{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] Xs = new int[n];
        int[] Ys = new int[n];
        int[] Hs = new int[n];
        for (int i = 0; i < n; i++){
            Xs[i] = sc.nextInt();
            Ys[i] = sc.nextInt();
            Hs[i] = sc.nextInt();
        }
        int xl = 200000001, xr = -200000001, yl = 200000001, yr = -200000001;
        for (int i = 0; i < n; i++){
            if (Xs[i] - Hs[i]< xl){
                xl = Xs[i] - Hs[i];
            }
            if (Xs[i] + Hs[i] > xr){
                xr = Xs[i] + Hs[i];
            }
            if (Ys[i] - Hs[i] < yl){
                yl = Ys[i] - Hs[i];
            }
            if (Ys[i] + Hs[i] > yr){
                yr = Ys[i] + Hs[i];
            }
        }

        int h = (int)Math.ceil((double) Math.max((xr - xl), (yr - yl)) / 2);
        int x = (int)Math.ceil((double)(xl + xr)/2);
        int y = (int)Math.ceil((double)(yl + yr)/2);
        System.out.println(x + " " + y + " " + h);
    }
}