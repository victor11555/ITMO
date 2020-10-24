import java.io.*;
import java.util.*;
import java.lang.*;

public class D {

    static int n;
    static int cnt;

    private static int func(int d){
        if( cnt == n - 1) return 1;
        cnt++;
        int res = 0;
        switch( d ){
            case 0: res = func(4) + func(6) ;
            case 1: res = func(6) + func(8) ;
            case 2: res = func(7) + func(9) ;
            case 3: res = func(4) + func(8) ;
            case 4: res = func(0) + func(3) + func(9);
            case 5: res = 0;
            case 6: res = func(1) + func(7) + func(0);
            case 7: res = func(6) + func(2);
            case 8: res = func(1) + func(3);
            case 9: res = func(4) + func(2);
        }
        cnt--;
        return res;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        int result = 0;
        cnt = 0;
        for (int i = 1; i < 8; i++) {
           result+= func(i);
        }
        result+= func(9);
        System.out.println(result%1000000000);
    }
}
