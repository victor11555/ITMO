package com.company;

import java.util.Scanner;
public class Main {
    private static int findfirst(int a, int[] arr){
        int le = -1;
        int mid;
        int ri = arr.length;
        while (le < ri - 1) {
            mid = (le + ri) >>> 1;
            if (arr[mid] < a) {
                le = mid;
            } else {
                ri = mid;
            }
        }
        if (ri == 0) return arr[ri];
        if (ri == arr.length) return arr[ri-1];
        if(Math.abs(arr[ri-1]-a) <=Math.abs(arr[ri]-a)){
            return arr[ri-1];
        }else {
            return arr[ri];
        }
    }

    public static void main(String[] args) {
        int n,k;
        Scanner sc= new Scanner(System.in);
        n = sc.nextInt();
        k= sc.nextInt();

        int[] arr = new int[n];
        int[] arr2 = new int[k];
        for (int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }

        for (int i =0; i<k; i++) {
            arr2[i] = sc.nextInt();
            System.out.println(findfirst(arr2[i], arr));
        }
    }
}