package com.company;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
	private static long count;

	public static int[] mergesort(int[] arr) {
		if (arr.length <= 1) {
			return arr;
		}

		int middleindex = arr.length / 2;
		int[] left = new int[middleindex];
		int[] right= new int[middleindex];

		left = Arrays.copyOfRange(arr, 0, middleindex);
		right = Arrays.copyOfRange(arr, middleindex, arr.length);

		int[] res = new int[arr.length];

		left = mergesort(left);
		right = mergesort(right);
		res = merge(left, right);
		return res;
	}


	private static int[] merge(int[] left, int[] right) {

		int[]  res1 = new int[left.length + right.length];
		int leftpoint, rightpoint, respoint;
		leftpoint = rightpoint = respoint = 0;

		while (leftpoint < left.length || rightpoint < right.length) {

			if (leftpoint < left.length && rightpoint < right.length) {

				if(left[leftpoint] < right[rightpoint]) {
					res1[respoint++] = left[leftpoint++];
				} else {
					res1[respoint++] = right[rightpoint++];
					count +=left.length-leftpoint;
				}
			} else if (leftpoint < left.length) {
				res1[respoint++] = left[leftpoint++];
			} else if (rightpoint < right.length) {
				res1[respoint++] = right[rightpoint++];
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