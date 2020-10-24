package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> arr = new ArrayList<>();
        while(sc.hasNextInt()){
            arr.add(sc.nextInt());
        }

        int[] arrdop = new int[101];

        for (int i = 0; i < arr.size(); i++) {
            arrdop[arr.get(i)]++;
        }
        int k = 0;
            for (int j = 0; j < 101; j++) {
                for (int i = 0; i < arrdop[j]; i++) {
                    arr.set(i+k, j);
                }
                k+=arrdop[j];
            }
        for (int i = 0; i < arr.size(); i++){
            System.out.print(arr.get(i)+ " ");
        }
    }
}
