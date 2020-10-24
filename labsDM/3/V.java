import java.io.*;
import java.util.*;



public class V {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("part2num.in"));
        String str = sc.nextLine();
        ArrayList<Integer> part = new ArrayList();
        Integer res = 0;
        int curr = 0, sum = 0;

        String[] strSplt = str.split("\\+");
        for(String el : strSplt) {
            part.add(Integer.valueOf(el));
        }

        int n = 0;
        for (int i = 0; i < part.size(); i++){
            n += part.get(i);
        }

        int[][] d = new int[n+1][n+1];

        for (int i = 1; i <= n; i++) {
            d[i][i] = 1;
            for (int j = i-1; j >=1; j--) {
                d[i][j] = d[i][j+1] + d[i - j][j];
            }
        }



        for (int i = 0; i < part.size(); i++){
            for (int j = curr; j < part.get(i); j++){
                res += d[n - sum - j][j];
            }
            sum += part.get(i);
            curr = part.get(i);
        }


        try(FileWriter writer=new FileWriter("part2num.out")){
            writer.write(res.toString());
        }catch(IOException ex){}
    }
}