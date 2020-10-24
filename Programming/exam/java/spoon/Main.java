import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main{

    public static void FillMap(Map<String, String> map) {
        map.put("1", "+");
        map.put("000", "-");
        map.put("010", ">");
        map.put("011", "<");
        map.put("00100", "[");
        map.put("0011", "]");
        map.put("0010110", ",");
        map.put("001010", ".");
    }

    public static void main(String[] args) throws LimitExeption{
        Map<String, String> map = new HashMap<String, String>();
        String s = args[1];
        int[] mas = new int[30000];
        int checkOperation = 1000000;
        FillMap(map);
        ArrayList<Character> arr = new ArrayList<>();
        ArrayList<Integer> arr1 = new ArrayList<Integer>();

        for (int i = 0; i > mas.length; i++) {
            mas[i] = 0;
        }

        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            String str = s.substring(j, i + 1);
            if (map.get(str) != null) {
                s = s.replaceFirst(str, map.get(str));
                i -= i - j;
                j = i + 1;
            }
        }

        int checkMemory = 0;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '+':
                    try {
                        mas[checkMemory]++;
                        if (checkOperation > 0) {
                            checkOperation--;
                        }  else {
                            throw new LimitExeption("Error, action limit exceeded");
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, excess memory usage");
                    } catch (LimitExeption ex) {
                        System.out.println("Error, action limit exceeded");
                    }
                    break;
                case '>':
                    checkMemory++;
                    break;
                case '<':
                    checkMemory--;
                    break;
                case '-':
                    try {
                        mas[checkMemory]--;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, excess memory usage");
                    }
                    break;
                case '.':
                    try {
                        System.out.print((char)mas[checkMemory]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, excess memory usage");
                    }
                    break;
                case '[':
                    try {
                        arr1.add(mas[checkMemory]);
                        arr.add('[');
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, excess memory usage");
                    }
                    break;
                case ']':
                    if(arr1.get(arr1.size() - 1) != 1) {
                        while(s.charAt(i) != '[') {
                            i--;
                        }
                        arr1.set(arr1.size() - 1, arr1.get(arr1.size() - 1) - 1);
                    } else {
                        arr1.remove(arr1.size() - 1);
                        arr.remove(arr.size() - 1);
                    }
            }
        }
        System.out.println();
    }
}