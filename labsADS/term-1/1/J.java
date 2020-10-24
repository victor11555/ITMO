package com.company;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double Vp = sc.nextDouble(), Vf = sc.nextDouble(), a = sc.nextDouble(), x = 0, s = 0, x1 = 0, min = (Vp + Vf)*2;
        while (x<=1){
            s= Math.sqrt((1-a)*(1-a)+x * x)/ Vp + Math.sqrt(a * a + (1-x)*(1-x))/ Vf;
        if (s<min){
            min = s;
            x1 = x;
        }
        x +=0.0001;
        }
        System.out.println(x1);
    }
}
