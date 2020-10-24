package com.company;
import java.util.Scanner;
public class Main {



    private static void siftup (int v1,int heap[]){
        while ((v1 != 0) && (heap[v1] > heap[(v1 - 1)/2])) {
            int c;
            c = heap[v1];
            heap[v1] = heap[(v1-1)/2];
            heap[(v1-1)/2] = c;
            v1 = (v1-1)/2;
        }
    }
    private static void siftdown(int v, int heap[]){
        while (2*v + 2 < heap.length){
            if ((heap[2*v + 2] > heap[2*v + 1]) && heap[2*v + 2] > heap[v]){
                int c;
                c = heap[v];
                heap[v] = heap[2*v + 2];
                heap[2*v + 2] = c;
            v = 2*v +2;
            } else if (heap[2*v + 1] > heap[v]){
                int c;
                c = heap[v];
                heap[v] = heap[2*v + 1];
                heap[2*v + 1] = c;
                v = 2*v +1;
            } else {
             break;
            }
        }
    }
    public static void main(String[] args) {
        int n;
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
         int[] heap = new int[n];
        for (int i = 0; i < n; i++) {
        heap[i] = 0;
        }
        int com;
        for (int i = 0; i < n; i++){
            com = sc.nextInt();
            if(com == 0) {
                heap[i] = sc.nextInt();
                siftup(i, heap);
            }

            if(com == 1){
                System.out.println(heap[0]);
                heap[0] = heap[n-1];
                heap[n-1] = 0;
                siftdown(0, heap);
            }
        }
    }
}
