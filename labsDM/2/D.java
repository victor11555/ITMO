import java.util.*;
import java.io.*;
import java.lang.*;
import java.nio.charset.StandardCharsets;
public class D{
	public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("lzw.in"));
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new TreeMap<>();
        String s1 = "abcdefghijklmnopqrstuvwxyz";
        int i = 0, k = 0, j = 26, g = 0;
        for ( i = 0; i < 26; i++) {
            map.put(Character.toString(s1.charAt(i)) , i);
        }
        i = 1;
        if (sc.hasNextLine()) {
            String s = sc.nextLine() + " ";

            while (i < s.length()) {
                if (map.get(s.substring(k, s.length()-1)) == null) {
                        g = 0;
                        i++;
                        while (map.get(s.substring(k, i)) != null) {
                            i++;
                            g++;
                        }
                        if (g > 1) {
                            map.put(s.substring(k, i), j);
                            j++;
                            i--;
                            sb.append(map.get(s.substring(k, i))).append(" ");
                        } else {
                            i--;
                            sb.append(map.get(s.substring(k, i))).append(" ");
                            map.put(s.substring(k, i+1), j);
                            j++;
                        }
                        k = i;
                        i = k + 1;
                } else {
                    i = s.length();
                    sb.append(map.get(s.substring(k, i-1)));
                }
            }
            try(FileWriter writer = new FileWriter("lzw.out")) {
                writer.write(sb.toString());
            }catch(IOException ex){ }
        } else {try(FileWriter writer = new FileWriter("lzw.out")) {
            writer.write("");
        }catch(IOException ex){ } }
    }
}