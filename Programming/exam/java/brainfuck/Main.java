import java.util.ArrayList;

public class Main{

    public static void main(String[] args) {

        StringBuilder s = new StringBuilder();
        for (String arg : args) { s.append(arg); }

        int[] result = new int[30000];

        ArrayList<Character> arr = new ArrayList<>();
        ArrayList<Integer> tmparr = new ArrayList<>();

        int k = 0;

        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
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