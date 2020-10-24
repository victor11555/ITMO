import java.util.Scanner;
import java.io.*;

public class H {

    public static int counter = 0;

    static Boolean Calc(int bit_no, String buf, StringBuilder sb) {
        int end;
        if (bit_no == 0) {
            H.counter ++;
            System.out.println(H.counter);
            sb.append(buf + "\n");
        } else {
            end = 1;

            if (buf.length() !=0 && buf.substring(buf.length() - 1).equals("1")) {
                end = 0;
            }
            for (int i = 0; i <= end; i++) {
                Calc(bit_no-1, buf+String.valueOf(i), sb);
            }
        }

        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("vectors2.in"));
        StringBuilder sb = new StringBuilder();
        int n = sc.nextInt();
        Calc(n, "", sb);


        try (FileWriter writer = new FileWriter("vectors2.out")) {
            sb.setLength(sb.length() - 1);
            String str1 = sb.toString();
            str1 = String.valueOf(H.counter) + "\n" + str1;
            writer.write(str1);

        } catch (IOException ex) {
        }
    }
}