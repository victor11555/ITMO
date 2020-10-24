import java.util.*;

public class F {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> arr = new ArrayList<>();
        ArrayList<Integer> array = new ArrayList<>();

        int x, i = 0, n = sc.nextInt();

        for(int k  = 0; k < n; k++){
            x = sc.nextInt();
            i = array.size() - 1;

            while (i >= 0 && array.get(i) < x) {
                sb.append("pop" + "\n");
                arr.add(array.get(i));
                array.remove(i);
                i--;
            }
            sb.append("push" +"\n");
            array.add(x);
        }


        while (array.size() != 0) {
            i = array.size() - 1;
            sb.append("pop" + "\n");
            arr.add(array.get(i));
            array.remove(i);
        }
        
        for (i = 1; i < arr.size(); i++) {
            if (arr.get(i - 1) > arr.get(i)) {
                System.out.println("impossible");
                return;
            }
        }
        System.out.println(sb.toString());
    }
}