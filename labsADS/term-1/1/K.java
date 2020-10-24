package com.company;
import java.util.Scanner;
import java.util.Arrays;
public class Main {

    private static boolean nextComb(int[] arr, int n){

        for (int i = arr.length - 1; i >= 0; i--){
            if (arr[i] < n - arr.length +i +1){
                arr[i]++;
                for (int j = i + 1; j < arr.length; j ++){
                    arr[j] = arr[j-1] + 1;
                }
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int n,k;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        k = sc.nextInt();
        int[] v = new int[n];
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = sc.nextInt();
            w[i] = sc.nextInt();
        }
        int wSum, vSum;
        double max = 0;
        double dev;
        int[] maxComb = new int[k];
        int[] comb = new int[k];
        for (int i =0; i < k; i++){
            comb[i] = i + 1;
            maxComb[i]=comb[i];
        }
        do {
            vSum = 0;
            wSum = 0;
            for (int i =0; i < k; i++){
                vSum += v[comb[i]-1];
                wSum += w[comb[i]-1];
            }
            dev = (double) vSum/(double) wSum;
            if (dev > max){
                max = dev;
                maxComb = Arrays.copyOfRange(comb,0,k);
            }
        } while (nextComb(comb, n));
        for (int i =0; i < k; i++) {
            System.out.println(maxComb[i]);
        }
    }
}