import java.util.ArrayList;
import java.util.Scanner;
public class C {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer> id = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int num = sc.nextInt(), q;
            if (num == 1) {
                id.add(sc.nextInt());
            } else if (num == 2) {
                id.remove(0);
            } else if (num == 3) {
                id.remove(id.size()-1);
            } else if (num == 4) {
                q = sc.nextInt();
                System.out.println(id.indexOf(q));
            } else {
                System.out.println(id.get(0));
            }
        }
    }
}