import java.util.Arrays;
import java.util.Scanner;
public class Main {
    private static long count;
    private static int[] mergesort(int[] arr){
        if (arr.length <= 1) {
            return arr;
        }
        int[] left;
        int[] right;
        left = Arrays.copyOfRange(arr, 0, arr.length/2);
        right = Arrays.copyOfRange(arr, arr.length/2, arr.length);
        int[] res = new int[arr.length];
        left = mergesort(left);
        right = mergesort(right);
        res = merge(left, right);
        return res;
    }
    private static int[] merge(int[] left, int[] right) {
        int[]  res1 = new int[left.length + right.length];
        int l, r;
        l = r = 0;
        while (l < left.length && r < right.length) {
            if(left[l] <= right[r]) {
                res1[l+r] = left[l++];
            } else {
                res1[l+r] = right[r++];
                count+= left.length-l;
            }
        }
        return res1;
    }
    public static void main(String[] args) {
        int n;
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++){
            arr[i] = in.nextInt();
        }
        arr = mergesort(arr);
        System.out.println(count);
    }
}