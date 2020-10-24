import java.io.*;
import java.util.*;

public class C {
    public static void main(String[] args) throws FileNotFoundException{
        String s, result = "";
        String s1 = "abcdefghijklmnopqrstuvwxyz";
        ArrayList<String> str = new ArrayList<>();
        ArrayList<String> alph = new ArrayList<>();

        Scanner sc = new Scanner(new File("mtf.in"));
        s = sc.nextLine();
        int n = s.length();

        for (int i = 0; i < n; i++) {
            str.add(Character.toString(s.charAt(i)));
        }

        for (int i = 0; i < 26; i++) {
            alph.add(Character.toString(s1.charAt(i)));
        }

        for (int i = 0; i < n; i++) {

            result =  result + " " + (alph.indexOf(str.get(i))+1);
            String tmp = alph.get(alph.indexOf(str.get(i)));
            alph.remove(alph.indexOf(str.get(i)));
            alph.add(0, tmp);

        }
        try(FileWriter writer = new FileWriter("mtf.out")) {
                writer.write(result);
        }catch(IOException ex){ }
    }
}