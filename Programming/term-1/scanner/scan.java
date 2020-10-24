import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.lang.*;
import java.util.*;
import java.io.FileInputStream;



public class Scan {

    private int current = 0, len = 0;
    private char[] buffer;
    private boolean EOF = false;
    private InputStreamReader in;


    public Scan(InputStream mode) throws IOException {
        this.in = new InputStreamReader(mode, StandardCharsets.UTF_8);
        buffer_extend();
    }

    public Scan(String line) throws IOException {
        this.in = new InputStreamReader(new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
        buffer_extend();
    }

    public Scan(File file) throws FileNotFoundException {
        this.in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        try {
            buffer_extend();
        } catch (IOException e) {
            System.out.print(e);
        }
    }

    public boolean buffer_extend() throws IOException {
        if (in.ready()){
            this.buffer = new char[100];
            this.len = in.read(this.buffer);
            return true;
        } else {
            return false;
        }
    }

    public int nextChar() throws IOException {
        if (this.current == 100) {
            this.current = 0;
            if (!buffer_extend()) {
                return -1;
            }
        }
            return (int) this.buffer[this.current++];
    }

    public String next() throws IOException {
        StringBuilder string = new StringBuilder();
        char k;
        int sym = 0;
        while (hasNext()) {
            k = (char) nextChar();
            if (Character.isWhitespace(k)) {
                if (sym == 1) {
                    break;
                } else {
                    string.append(k);
                    sym = 1;
                }
            }
        }
        if (string.length() > 0) {
            return string.toString();
        } else {
            return "";
        }
    }

    public String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();
        char k = '1';
        int ch = 0;
        while (k != '\n' && nasNext()) {
            k = (char) nextChar();
            if (k != '\n'){
                line.append((char) k);
            }
            if (!Character.isWhitespace((char) k) {
                ch == 1;
                }
            } if (ch == 1){
            return line.toString();
        } else {
            return "";
        }
    }

    public boolean hasNext() throws IOException {
        if (this.current < this.len){
                return true;
            }else{
        return in.ready();
        }
    }

    public void close() throws IOException{
        in.close();
    }
}