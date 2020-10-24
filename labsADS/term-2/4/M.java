import java.util.*;
import java.io.*;

public class M {
    public static void main(String[] args) throws IOException {
        //try (FileWriter writer = new FileWriter("set.out")) {
        File fout = new File("map.out");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        Map<String, String> set = new HashMap<String, String>(1000000);
        Scanner sc = new Scanner(new File("map.in"));
        String x, y;
        while (sc.hasNext()) {
            String op = sc.next();

            switch (op) {
                case "put":
                    x = sc.next();
                    y = sc.next();
                    set.put(x, y);
                    break;
                case"get":
                    x = sc.next();
                    if (set.containsKey(x)) {
                        bw.write(set.get(x));
                        bw.newLine();
                    }
                    else
                        bw.write("none\n");
                    break;
                case "delete":
                    x = sc.next();
                    set.remove(x);
                    break;
            }
        }
        sc.close();
        bw.close();
        fos.close();
    }
}