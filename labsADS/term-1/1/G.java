package com.company;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        int y, x, a, n, b, s;
        s = 0;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        x = sc.nextInt();
        y = sc.nextInt();

        if (x<y){
            a = x;
            s+=x;
        } else {
            a = y;
            s+=y;
        }
        int t=0;
        int cx=0;
        int cy=0;
        for (int i=0;i<x*y*n;i++){
            if (i % x = 0) {
                cx++;
            }
            if (i % y =0){
                cy++
            }
            if (cx+cy+1=n){
                break;
            }
        }

        System.out.println(i + 1);
    }
}
