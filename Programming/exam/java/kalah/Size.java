import java.util.Scanner;
import java.io.*;

public class Size {
    public int kol; //сколько в одной лунке
    //public int ko;
    public int[] kalah = new int[2];
    public int[][] oneTwo = new int[2][6];
    public Player[] id;
    public boolean flags = false;

    Size() {
        kalah[0] =0;
        kalah[1] = 0;
        boolean flag = true;
        while(flag) {
            Scanner sc = new Scanner(System.in);
            try{
                flag = false;
                System.out.println("Write number for one tee");
                kol = sc.nextInt();
                if (kol < 3 || kol > 6) {
                    System.err.println("Invalid value number"); 
                    flag = true;
                }
            } catch (Exception e) {
                flag = true;
                System.err.println("Invalid input format"); 
            }
        }
        for (int i = 0; i < 6; i++) {
            oneTwo[0][i] = kol;
            oneTwo[1][i] = kol;
        }
    }
}