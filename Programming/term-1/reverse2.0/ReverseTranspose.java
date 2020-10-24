import java.io.IOException;
import java.util.*;
import java.io.*;

public class ReverseTranspose {
    public static void main (String[] args) {
        try {
            FastScanner sc = new FastScanner(System.in);
            ArrayList<ArrayList<Integer>> array = new ArrayList<>();
            while (sc.hasNextLine()) {
                int i = 0;
                FastScanner scs = new FastScanner(sc.nextLine());
                while (scs.hasNextInt()) {
                    if (i < array.size()) {
						array.get(i).add(scs.nextInt());
				    } else {
                        ArrayList<Integer> temp = new ArrayList<>();
					    temp.add(scs.nextInt());
					    array.add(temp);
					}
					i++;
				}
			}
            int j = 0;
            for (int i = 0; i < array.size(); i++) {
				for (j = 0; j < array.get(i).size(); j++) {
					System.out.print(array.get(i).get(j) + " ");
				}
				System.out.println();
			}
        } catch(IOException e) {
            System.out.println("err");
        } catch(InputMismatchException e) {
            System.out.println("errr");
        }
    }
}