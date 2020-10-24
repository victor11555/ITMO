package com.company;
import java.util.Arrays;
import java.util.Scanner;

public class Main{

    private static int func(int[] arr, int l, int r) {
        int s= 0;
        r++;
        s = last(arr, r, l) - first(arr, l);
        return s;
    }

    private static int first (int[] arr, int g) {
        int l = -1;
        int r = arr.length;
        while (l < r - 1) {
            int mid = (l + r) >>> 1;
            int Val = arr[mid];
            if (Val < g) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return r;
    }

    private static int last (int[] arr, int g1, int l) {
        int r = arr.length;
        l--;
        while (l < r - 1) {
            int mid = (l + r) >>> 1;
            int Val1 = a[mid];
            if (Val1 < g1) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return r;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];

        for (i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);
        int k = sc.nextInt();
        for (i = 0; i < k; i++){
            int l = sc.nextInt();
            int r = sc.nextInt();
            System.out.print( func(arr, l, r) + " ");
        }
    }
}