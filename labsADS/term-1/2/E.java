import java.util.*;
public class E {
    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine() + " ";
        int i = 0;
        
        while (i < s.length()) {
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                int j = i;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    i++;
                }
                array.add(Integer.parseInt(s.substring(j, i)));
                i++;
            }

            while (i < s.length() && !Character.isDigit(s.charAt(i))) {
                int a = array.size()-1;
                int x = array.get(a - 1);
                int y = array.get(a);
                array.remove(a);
                array.remove(a - 1);
                
                if (s.charAt(i) == '+') {
                    array.add(x + y);
                } else if (s.charAt(i) == '-') {
                    array.add(x - y);
                } else if (s.charAt(i) == '*') {
                    array.add(x * y);
                }
                i += 2;
            }
        }
        System.out.print(array.get(0));
    }
}