import java.io.*;
import java.util.*;



public class J{
    private static boolean nextComb(List<Integer> comb){
        comb.set(comb.size() - 1, comb.get(comb.size() - 1) - 1);
        comb.set(comb.size() - 2, comb.get(comb.size() - 2) + 1);
        if (comb.get(comb.size() - 2) > comb.get(comb.size() - 1)) {
            comb.set(comb.size() - 2, comb.get(comb.size() - 2) + comb.get(comb.size() - 1));
            comb.remove(comb.size() - 1);
        } else {
            while (comb.get(comb.size() - 2) * 2 <= comb.get(comb.size() - 1)) {
                comb.add(comb.get(comb.size() - 1) - comb.get(comb.size() - 2));
                comb.set(comb.size() - 2, comb.get(comb.size() - 3) );
            }
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("partition.in"));
        Integer n = sc.nextInt();
        List<Integer> comb = new ArrayList<Integer>();
        for(int i = 0; i < n; i++) {
            comb.add(1);
        }

        try (FileWriter writer = new FileWriter("partition.out")) {
            while(comb.size() > 1) {
                for(int i = 0; i < comb.size()-1; i++) {
                    writer.write(comb.get(i) + "+");
                }
                writer.write(Integer.toString(comb.get(comb.size()-1)));
                writer.write(System.lineSeparator());
                nextComb(comb);
            }
            for(int i = 0; i < comb.size()-1; i++) {
                writer.write(comb.get(i) + "+");
            }
            writer.write(Integer.toString(comb.get(comb.size()-1)));
        } catch (IOException ex) { }
    }
}
