import java.io.*;
import java.util.*;



public class E {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("telemetry.in"));
        int n = sc.nextInt();
        int k = sc.nextInt();
        int f = 0;
        List<String> res = new ArrayList<String>();
        List<String> res1 = new ArrayList<String>();
        for (int i = 0; i < k; i++) {
            res.add(Integer.toString(i));
        }
        for (int i=1; i<n;i++) {
            res1 = new ArrayList<String>();
            for (int j=0; j<k;j++) {
                for (String el : res) {
                    res1.add(el + j);
                }
                Collections.reverse(res);
            }
            res = res1;
        }

        try (FileWriter writer = new FileWriter("telemetry.out")) {
            for (String el : res) {
                writer.write(el);
                writer.write(System.lineSeparator());
            }
        } catch (IOException ex) { }
    }
}