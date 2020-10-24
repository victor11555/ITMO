import java.util.*;
import java.io.*;

public class O {
    public static void main(String[] args) throws IOException {
        //try (FileWriter writer = new FileWriter("set.out")) {
        File fout = new File("multimap.out");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        HashMap<String, String> setY = new HashMap<String, String>();
        Map<String, HashMap<String, String>> setX = new HashMap<String, HashMap<String, String>>();
        Scanner sc = new Scanner(new File("multimap.in"));
        String x, y;
        while (sc.hasNext()) {
            String op = sc.next();

            switch (op) {
                case "put":
                    x = sc.next(); y = sc.next();
                    setY = setX.get(x);
                    if (setY == null) {
                        setY = new HashMap<String, String>();
                        setX.put(x, setY);
                    }
                    setY.put(y, "1");
                    break;

                case"get":
                    x = sc.next();
                    setY = setX.get(x);
                    int number = 0;
                    if (setY != null) {
                        number = setY.size();
                        bw.write(String.valueOf(number));
                        bw.write(" ");
                        bw.write(String.join(" ", setY.keySet()));
                        bw.newLine();
                    } else bw.write("0\n");
                    break;

                case "delete":
                    x = sc.next(); y = sc.next();
                    setY = setX.get(x);
                    if (setY != null) {
                        setY.remove(y);
                    }
                    break;

                case "deleteall":
                    x = sc.next();
                    setX.remove(x);
                    break;
            }
        }
        sc.close();
        bw.close();
        fos.close();
    }
}