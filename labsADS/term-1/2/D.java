import java.io.DataInputStream;
import java.io.InputStream;
import java.util.*;
public class D {
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

        public String nextString(int maxSize) {
            byte[] ch = new byte[maxSize];
            int point =  0;
            try {
                byte c = read();
                while (c == ' ' || c == '\n' || c=='\r')
                    c = read();
                while (c != ' ' && c != '\n' && c!='\r') {
                    ch[point++] = c;
                    c = read();
                }
            } catch (Exception e) {}
            return new String(ch, 0,point);
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

    public static void main(String[] args) {
        FastScanner sc = new FastScanner(System.in);
        int n = sc.nextInt(),count2 = 0, count1 = 0;
        StringBuilder sb = new StringBuilder();
        ArrayDeque<Integer> id1 = new ArrayDeque<>();
        ArrayDeque<Integer> id2 = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            String num = sc.nextString(2);
            if (num.equals("+")) {
                id2.addLast(sc.nextInt());
                count2 ++;
            } else if (num.equals("*")) {
                id2.addFirst(sc.nextInt());
                count2 ++;
            } else if (num.equals("-")){
                sb.append(id1.pop()).append("\n");
                count1 --;
            }

            if (count2 > count1){
                id1.addLast(id2.pop());
                count1 ++;
                count2 --;
            }
        }
        System.out.println(sb);
    }
}