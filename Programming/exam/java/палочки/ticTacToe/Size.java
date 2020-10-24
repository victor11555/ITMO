package ticTacToe;
import java.util.Scanner;
import java.io.*;

public class Size {
    public int m;
    public int n;
    public int x;
    public int o;
    public boolean flags = false;
    public Player[] id;

    Size() {
        boolean flag = true;
        while(flag) {
            Scanner sc = new Scanner(System.in);
            try{
                flag = false;
                System.out.println("Write m, n");
                m = sc.nextInt()+1;// col
                n = sc.nextInt()*2+1;//row
                if (m <= 1 || n < 3) {
                    System.err.println("Invalid values mn");
                    flag = true;
                }
            } catch (Exception e) {
                flag = true;
                System.err.println("Invalid input format"); 
            }
        }
    }
}