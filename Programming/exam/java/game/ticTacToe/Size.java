package ticTacToe;
import java.util.Scanner;
import java.io.*;

public class Size {
    public int m;
    public int n;
    public int k;
    public int ko;
    public Player[] id;

    Size() {
        boolean flag = true;
        while(flag) {
            Scanner sc = new Scanner(System.in);
            try{
                flag = false;
                System.out.println("Write m, n");
                m = sc.nextInt();
                n = sc.nextInt();
                k = 4;
                if (m <= 0 || n <= 0 || k > m && k > n) {
                    System.err.println("Invalid value mn"); 
                    flag = true;
                }
            } catch (Exception e) {
                flag = true;
                System.err.println("Invalid input format"); 
            }
        }
    }
}