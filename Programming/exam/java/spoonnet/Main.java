import java.util.ArrayList;
import java.util.*;

public class Main{

    public static void main(String[] args) {
        Map<String, Character> map = Map.of(
                "1", '+',
                "000", '-',
                "010", '>',
                "011", '<',
                "00100", '[',
                "0011", ']',
                "0010110", ',',
                "001010", '.'
        );

        StringBuilder s = new StringBuilder();
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        for (String arg : args) { s.append(arg); }


        for (int i = 0; i < s.length(); i++) {
            s1.append(s.charAt(i));
            if (map.containsKey(s1)){
                s2.append(map.get(s1));
                i += s1.length();
                s1 = new StringBuilder();
            }
        }


        int[] result = new int[30000];

        ArrayList<Character> arr = new ArrayList<>();
        ArrayList<Integer> tmparr = new ArrayList<>();

        int k = 0;

        for (int i = 0; i < s2.length(); i++) {
            switch (s2.charAt(i)) {
                case '>':
                    k++;
                    break;

                case '<':
                    k--;
                    break;


                case '+':
                    try {
                        result[k]++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, restriction on memory");
                    }
                    break;

                case '-':
                    try {
                        result[k]--;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, restriction on memory");
                    }
                    break;

                case '[':
                    try {
                        tmparr.add(result[k]);
                        arr.add('[');
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, restriction on memory");
                    }
                    break;

                case ']':
                    if(tmparr.get(tmparr.size() - 1) != 1) {
                        while(s.charAt(i) != '[') {
                            i--;
                        }
                        tmparr.set(tmparr.size() - 1, tmparr.get(tmparr.size() - 1) - 1);
                    } else {
                        tmparr.remove(tmparr.size() - 1);
                        arr.remove(arr.size() - 1);
                    }


                case '.':
                    try {
                        System.out.println(result[k]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, restriction on memory");
                    }
                    break;

            }
        }
    }
}