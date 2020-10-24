import java.util.ArrayList;
import java.util.Scanner;

public class B {

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            arr.add(sc.nextInt());
        }
        int i = 2;
        while (i < arr.size() && arr.size() > 2) {
            if (arr.get(i-2) == arr.get(i-1) && arr.get(i-1) == arr.get(i)) {
                int g = i+1;
                while (g < arr.size() && arr.get(i) == arr.get(g)) {
                    g++;
                }
                for (int j = i-2; j < g; j++) {
                    arr.remove(i-2);
                }
                i = 2;
            } else {
                i++;
            }
        }
        System.out.print(n - arr.size());
    }
}