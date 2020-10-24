import java.io.*;
import java.util.*;
import java.lang.*;

public class A {
    private static Stack<Integer> s = new Stack<Integer>();
    private static Stack<Integer> mn = new Stack<Integer>();

    static class FastScanner {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastScanner(InputStream in) {
            din = new DataInputStream(in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead =  0;
        }

        public int nextInt() {
            int ret =  0;
            boolean neg;
            try {
                byte c = read();
                while (c <= ' ')
                    c = read();
                neg = c == '-';
                if (neg)
                    c = read();
                do {
                    ret = ret * 10 + c - '0';
                    c = read();
                } while (c > ' ');
                if (neg) return -ret;
            } catch (Exception e) {}
            return ret;
        }

        private void fillBuffer() {
            try {
                bytesRead = din.read(buffer, bufferPointer =  0, BUFFER_SIZE);
            } catch (Exception e) {}
            if (bytesRead == -1) buffer[ 0] = -1;
        }

        private byte read() {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer++];
        }
    }

    public static void push(int x) {
        s.push(x);
        if (mn.empty()) {
            mn.push(x);
        } else {
            mn.push(Math.min(x, mn.peek()));
        }
    }

    public static void main(String[] args) {
        int n, x, c = 0;
        StringBuilder sb = new StringBuilder();
        FastScanner sc = new FastScanner(System.in);
        n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++){
            x = sc.nextInt();
            if ( x == 1){
                push (sc.nextInt());
            } else if (x == 2){
                s.pop();
                mn.pop();
            } else{
                sb.append(mn.peek()).append("\n");
            }
        }
        System.out.println(sb);
    }
}
