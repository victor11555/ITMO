import java.io.*;
import java.util.*;



public class I {

    private static String nextComb(String str){
        int cntL = 0;
        int cntR = 0;
        for(int i = str.length()-1 ; i >= 0  ; i--) {
            if (str.charAt(i) == '(') {
                cntL ++;
                if (cntR > cntL) break;
            } else {
                cntR ++;
            }
        }
        str = str.substring(0, str.length() - cntL - cntR);
        if (str.isEmpty()) {
            return "false";
        } else {
            str = str +')';
            for (int j = 0; j < cntL; j++) {
                str = str +'(';
            }
            for (int j = 0; j < cntR - 1; j++) {
                str = str +')';
            }
            return str;
        }
    }


    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("brackets.in"));
        int n = sc.nextInt();

        String str = "";
        for (int j = 0; j < n; j++) {
            str = str +'(';
        }
        for (int j = 0; j < n; j++) {
            str = str +')';
        }

        try (FileWriter writer = new FileWriter("brackets.out")) {
            writer.write(str);
            str = nextComb(str);
            writer.write(System.lineSeparator());
            while (str != "false") {
                writer.write(str);
                writer.write(System.lineSeparator());
                str = nextComb(str);
            }
        } catch (IOException ex) { }
    }
}