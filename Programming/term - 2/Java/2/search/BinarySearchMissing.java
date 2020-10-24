package search;
import java.util.ArrayList;
import java.util.Scanner;

public class BinarySearchMissing {
    
    public static int iterbinary (int[] a, int x) {
        //Invar: a[] отсортирован по невозрастанию
        //Pre: a.length-1 == k >=0 && Invar
        int l = -1, m, r = a.length-1;
        //Post: l == -1 && r == k >=0 && Invar
        //Invar: Invar && a.length > 0 && -1 <= l<r<=a.length-1 || a.length == 0
        //Pre: (r-l >= 1 && a[r] <= x || a.length == 0) && Invar
        while (a.length > 1 && r - l > 1) {
            //Pre: (l+r)/2 == k > 0 && Invar
            m = (l + r) / 2;
            //Post: m == k >0 && Invar

            //Pre: r-l == k && Invar
            if (a[m] > x) {
                //Pre: m == 0 && Invar
                l = m;
                //Post: l == 0 && Invar
            } else {
                //Pre: m == 0 && Invar
                r = m;
                //Post: r == 0 && Invar
            }
            //Post: 1 <= r'-l' < k && Invar
        }
        //Post: -1 <= l'<r'<=a.length-1 && r'-l' == 1 && a[r'] <= x && a[r'-1] > x && Invar || a.length == 0
        if(r > - 1 && a[r] == x) {
            return r;
        } else if ( r == -1) {
            return -1;
        } if (a[a.length-1] > x) {
            return (-(r+1)-1);
        }
        return (-(r)-1);
        //Post: мин индекс i, что a[i] == x || -1 && a.length == 0 || -(a.length)-1 && a[a.length-1] > x || индекс i, что a[i-1] > x > a[i]
    }



    public static int recurbinary (int[] a, int x, int l,int r) {
        //Invar: a[-1]..a[l-1] > x >= a[r]..a[a.length]
        //a[-1] = +inf, a[a.length] = -inf
        // i <  j -> a[i] >= a[j]

        if (a.length > 1 && r - l > 1) {
            //Pre: (l+r)/2 == k > 0 && Invar
            int m = (l + r) / 2;
            //Post: m == k >0 && Invar

            //Pre:Invar
            if (a[m] > x) {
                //Pre: l == m && Invar
                return recurbinary(a, x, m, r);
                //Post: мин индекс i, что a[i] == x || -1 && a.length == 0 || -(a.length)-1 && a[a.length-1] > x || индекс i, что a[i-1] > x > a[i]
            } else {
                //Pre: r == m && I
                return recurbinary(a, x, l, m);
                //Post: мин индекс i, что a[i] == x || -1 && a.length == 0 || -(a.length)-1 && a[a.length-1] > x || индекс i, что a[i-1] > x > a[i]
            }
            //Post:Invar
        }

        //Post: r-l == 1 && a[r'] < x && a[r'-1] > x && Invar || a.length == 0

        if(r > - 1 && a[r] == x) {
            return r;
        } else if ( r == -1) {
            return -1;
        } if (a[a.length-1] > x) {
            return (-(r+1)-1);
        }
        return (-(r)-1);

        //Post: мин индекс i, что a[i] == x || -1 && a.length == 0 || -(a.length)-1 && a[a.length-1] > x || индекс i, что a[i-1] > x > a[i]
    }

    public static void main( String[] args){
        int x = Integer.parseInt(args[0]);
        int n = args.length-1;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(args[1+i]);
        }
        //Invar: arr[] отсортирован по невозрастанию

        //Pre: Invar
        System.out.println(iterbinary(arr, x));
        //Pre: Invar
        //System.out.println(recurbinary(arr,x, -1, n-1));
    }
}