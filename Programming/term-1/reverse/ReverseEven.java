import java.util.ArrayList;
import java.io.*;

public class ReverseEven {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> array = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList();
        String s;
        while (sc.hasNextLine()) {
            temp = new ArrayList();
            s = sc.nextLine();
            Scanner scs = new Scanner(s);
            while (scs.hasNextInt()) {
                int a = scs.nextInt();
                if (a % 2 == 0) {
                    temp.add(a);
                }
            }
            array.add(temp);
        }

        for (int i = array.size() - 1; i >= 0; i--) {
            temp = array.get(i);
            if (temp.size() == 0) {
                System.out.println();
                continue;
            }
            for (int j = temp.size() - 1; j >= 0; j--) {
                System.out.print(temp.get(j) + " ");
            }
            System.out.println();
        }
    }
}