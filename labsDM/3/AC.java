import java.io.*;
import java.util.*;



public class AC{
    private static boolean nextComb(List<Integer> comb){
        if (comb.size()>1) {
            comb.set(comb.size() - 1, comb.get(comb.size() - 1) - 1);
            comb.set(comb.size() - 2, comb.get(comb.size() - 2) + 1);
            if (comb.get(comb.size() - 2) > comb.get(comb.size() - 1)) {
                comb.set(comb.size() - 2, comb.get(comb.size() - 2) + comb.get(comb.size() - 1));
                comb.remove(comb.size() - 1);
            } else {
                while (comb.get(comb.size() - 2) * 2 <= comb.get(comb.size() - 1)) {
                    comb.add(comb.get(comb.size() - 1) - comb.get(comb.size() - 2));
                    comb.set(comb.size() - 2, comb.get(comb.size() - 3));
                }
            }
            return true;
        } else return false;

    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("nextpartition.in"));
        String str = sc.nextLine();
        String[] parts = str.split("=");
        Integer n=Integer.valueOf(parts[0]);
        String[] sum = parts[1].split("\\+");
        List<Integer> comb = new ArrayList<Integer>();
        List<String> list = Arrays.asList(sum);
        for(String el : list) {
            comb.add(Integer.valueOf(el));
        }


        try (FileWriter writer = new FileWriter("nextpartition.out")) {
            if (nextComb(comb)) {
                writer.write(n + "=");
                for (int i = 0; i < comb.size() - 1; i++) {
                    writer.write(comb.get(i) + "+");
                }
                writer.write(Integer.toString(comb.get(comb.size() - 1)));
            } else writer.write("No solution");
        } catch (IOException ex) { }
    }
}
