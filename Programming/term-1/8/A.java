import java.io.*;
import java.util.*;
public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt(), b = sc.nextInt(), n = sc.nextInt();
        System.out.println((int)(Math.ceil((double)(n-b)/(b-a)) * 2 + 1));
    }
}