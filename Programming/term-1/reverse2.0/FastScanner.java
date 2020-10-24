import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class FastScanner implements AutoCloseable {
	private int pos, len;
	private char[] buffer;
	private boolean EOF = false;
	private InputStreamReader is;

	public FastScanner(InputStream in) {
		is = new InputStreamReader(in, StandardCharsets.UTF_8);
	}

	public FastScanner(String in) {
		in += " ";
		is = new InputStreamReader(new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
	}

	public FastScanner(File in) throws IOException {
		is = new InputStreamReader(new FileInputStream(in), "UTF-8");
	}
	
	public char nextChar() throws IOException {
		if (pos >= len) {
			readBuffer();
		}
		return (char)buffer[pos++];
	}
	
	public boolean hasNextChar() throws IOException {
		nextChar();
		pos--;
		return !EOF;
	}
	
	public boolean hasNextLine() throws IOException {
		nextChar();
		pos--;
		return !EOF;
	}

	public String nextLine() throws IOException {
		StringBuilder sb = new StringBuilder();
		char x;
		while (hasNextChar()) {
			x = nextChar();
			if ( x == '\n' ) {
				break;
			}	
			if (x != '\r') {
				sb.append(x);
			}
		}
		return sb.toString();
	}

	public boolean hasNextInt() throws IOException {
		skipBlank();
		char x;
		boolean res = true;
		if (!hasNextChar()) {
			res = false;
		} else {
			if ( pos < len - 1) {
				x = nextChar();
				if (x != '\n' && x != '\r'){
					if (!Character.isDigit(x) && x != '-' && x != '+') {
						res = false;
					}
					pos--;
				}
			}
		}
		return res;
	}

	public int nextInt() throws IOException{
		StringBuilder sb = new StringBuilder();
        char x;
			skipBlank();
			if (hasNextChar() && pos < len - 1) {
				x = nextChar();
				if (x != '\n') {
					pos--;
				}
			} else {
				if (!hasNextChar()) {
					throw new InputMismatchException();
				}
			}
        	while (hasNextChar()){
				x = nextChar();
            	if (Character.isDigit(x) || x == '-' || x == '+'){
                	sb.append(x);
            	} else{
                	if (!Character.isWhitespace(x)){
                    	throw new InputMismatchException();
                	}
                	break;
				}				
			}
        try {
			if (sb.length() != 0) {
		   return Integer.parseInt(sb.toString());
			} else {
				throw new NumberFormatException();
			}
        } catch (NumberFormatException e){
           throw new InputMismatchException();
		}
	}

	public boolean hasNext() throws IOException {
		return hasNextLine();
	}

	public String next() throws IOException {
		skipBlank();
		StringBuilder sb = new StringBuilder();
		char x;
		while (hasNextChar()) {
			x = nextChar();
			if ( x == '\n' && sb.length() != 0 ) {
				break;
			}
			if (x != ' ') {
				sb.append(x);
			} else {
				break;
			}
		}
		if( sb.length() != 0) {
			return sb.toString();
		} else {
			if (EOF){
				throw new NoSuchElementException();
			}
		}
		return "";
	}

	private void readBuffer() throws IOException {
		this.buffer = new char[4096];
		this.len = is.read(this.buffer);
		while (len == 0) {
			this.len = is.read(buffer);
		}
		if (this.len == -1) {
			this.EOF = true;
		}
		this.pos = 0;
	}

    private void skipBlank() throws IOException {
        while (true) {
			if (hasNextChar()) {
				char x = nextChar();
            	if (!Character.isWhitespace(x)){
					pos--;
					break;
            	}
			} else {
				break;
			}
		}
    }

	public void close() throws IOException {
		is.close();
	}
}
