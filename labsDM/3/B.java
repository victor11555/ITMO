import java.io.*;
import java.util.*;

public class B {
    public static void zero(String str, int n, List<String> res) {
        if (n == 0) res.add(str);
        else {
            one(str + "1", n - 1, res);
            zero(str + "0", n - 1, res);
        }
    }

    public static void one(String str, int n, List<String> res) {
        if (n == 0) res.add(str);
        else {
            one(str + "0", n - 1, res);
            zero(str + "1", n - 1, res);
        }
    }

    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("gray.in"));
        int n = sc.nextInt();
        String str = "";
        List<String> res = new ArrayList<String>();
        one(str, n, res);
        try(FileWriter writer=new FileWriter("gray.out")){
            for (String el: res) {
                writer.write(el);
                writer.write(System.lineSeparator());
            }
        }catch(IOException ex){}
    }

}