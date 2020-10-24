package com.company;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        long w, h, n;
        Scanner sc = new Scanner(System.in);
        w = sc.nextLong();
        h = sc.nextLong();
        n = sc.nextLong();
        long w1 = w, h1 = h;
        n--;

        while (n > 0) {
            if (h+h1 <= w1) {
                h1 += h;
                n -= w1/w;
            } else if (w + w1 <= h1) {
                w1 += w;
                n -= w1/w;
            } else if(w1 + w <h1 + h) {
                w1+=w;
                n -= h1/h;
            } else {
                h1+=h;
                n -= w1/w;
            }
        }

        if (w1 > h1) {
            System.out.println(w1);
        } else {
            System.out.println(h1);
        }
    }
}
