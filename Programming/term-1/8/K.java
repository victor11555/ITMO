import java.io.*;
import java.util.*;

public class K{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        String[][] str = new String[n][m];

        TreeMap<String, String> map = new TreeMap<>();
        Integer i1 = 0;
        Integer j1 = 0;

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; i++){
                str[i][j] = sc.next();
                if (Character.isLetter(str[i][j])) {
                    i1 = i;
                    j1 = j
                    map.put(str[i][j], i1.toString() + j1.toString());

                }
            }
        }



        i1 = map.get(A).charAt(0);
        j1 = map.get(A).charAt(1);
        for(Map.Entry e : map.entrySet()) {
            e.getValue() + e.getKey();
        }



    }
}