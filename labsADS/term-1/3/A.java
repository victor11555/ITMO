import java.io.*;
import java.util.*;
import java.lang.*;

public class A {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("input.txt"));
        int n = sc.nextInt();
        int k =  sc.nextInt();
        int[] c = new int[n+1];
        int[] p = new int[n+1];
        Integer[] d = new Integer[n+1];
        ArrayList<Integer> res = new ArrayList();

        d[1] = 0;
        c[1] = 0;

        for (int i = 2; i < n; i++){
            c[i] = sc.nextInt();
        }

        for (int i = 2; i < n + 1; ++i){
            int max = i - 1;
            for(int j = Math.max(1, i - k); j <= i-1; ++j){
                if (d[j] > d[max]){
                    max = j;
                }
            }
            d[i] = d[max] + c[i];
            p[i] = max;
        }

        int t = n;
        res.add(t);
        do {
            t = p[t];
            res.add(t);
        } while (t>1);


        try(FileWriter writer=new FileWriter("output.txt")){

            writer.write(Integer.toString(d[n]));
            writer.write(System.lineSeparator());
            writer.write(Integer.toString(res.size()-1));
            writer.write(System.lineSeparator());
            for (int i = res.size()-1; i > -1; i--){
                String str = Integer.toString(res.get(i));
                writer.write(str + " ");
            }
        }catch(IOException ex){}

    }
}
