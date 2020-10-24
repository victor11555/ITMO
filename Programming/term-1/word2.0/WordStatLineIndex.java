import java.nio.charset.StandardCharsets;
import java.util.*;
import java.lang.*;
import java.io.*;

public class WordStatLineIndex {
    public static void main(String[] args) {
        Map<String, ArrayList<String>> map = new TreeMap<>();
        String str;
        File file = new File(args[0]);
        try (FastScanner sc = new FastScanner(file)) {
            int cnt = 1;
            while (sc.hasNext()) {
                try {
                    ArrayList<String> a = new ArrayList<>();
                    String s = sc.nextLine().toLowerCase();
                    StringBuilder tmp = new StringBuilder();
                    for (int i = 0; i < s.length(); i++) {
                        char x = s.charAt(i);
                        while (Character.isLetter(x) || Character.getType(x) == Character.DASH_PUNCTUATION || x == '\''){
                            tmp.append(x);
                            i++;
                            if (i < s.length()) {
                                x = s.charAt(i);
                            } else {
                                break;
                            }
                        }
                        if (!tmp.toString().isEmpty()) {
                            a.add(tmp.toString());
                            tmp.setLength(0);
                        }
                    }
                    for (int i = 0; i < a.size(); i++) {
                        str = a.get(i);
                        if (str.length() > 0) {
                            ArrayList<String> temp = map.getOrDefault(str, new ArrayList<>());
                            temp.add(cnt + ":" + (i + 1));
                            map.put(str, temp);
                        }
                    }
                cnt++;
                } catch (IOException e) {
                    System.err.println("Wrong data!");
                }
            }
            try (BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                for (Map.Entry<String, ArrayList<String>> mapEntry : map.entrySet()) {
                    wr.write(mapEntry.getKey() + " " + mapEntry.getValue().size());
                    for (String strTemp : mapEntry.getValue()) {
                        wr.write(" " + strTemp);
                    }
                    wr.write("\n");
                }
            } catch (IOException e) {
                System.err.println("OutputException");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found");
        } catch (IOException e) {
            System.err.println("InputException");
        }
    }
}