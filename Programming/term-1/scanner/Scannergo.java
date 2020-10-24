import java.util.*;
import java.io.*;
public class Scanner{
	private InputStream inputStream; 
    private BufferedReader reader; 
    private StringBuilder token = new StringBuilder();
    private int last = ' ';
    private String s = new String();
	
	public Scanner (String string) {
		reader = new BufferedReader(new StringReader(string));
	}
  
    public Scanner(InputStream inputStream) throws IOException { 
		this.inputStream = inputStream;
		reader = new BufferedReader(new InputStreamReader(inputStream));
	}
  
	public Scanner(File filename) throws FileNotFoundException {
		this.inputStream = new FileInputStream(filename);
		reader = new BufferedReader(new InputStreamReader(inputStream));
	}
  
	public Scanner(File filename, String encoding) throws FileNotFoundException, UnsupportedEncodingException {
		this.inputStream = new FileInputStream(filename);
		reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
	}

	private void skipWhiteSpace() throws IOException {
	    while (Character.isWhitespace(last)) {
			last = reader.read();
		}
		while (!Character.isWhitespace(last) && last != -1) {
		  token.append((char)last);
		  last = reader.read();
		}
	}
	
	public boolean hasNext() throws IOException {
		while (Character.isWhitespace(last)) {
			last = reader.read();
		}
		return (last != -1);
	}
  
	public String next() throws IOException {
		if (token.length() == 0) {
			skipWhiteSpace();
		}
		String s = token.toString();
		token.setLength(0); 
		return s;
	}
  
	public boolean hasNextInt() throws IOException {
		if (token.length() == 0) {
			skipWhiteSpace();
		}
		try {
			Integer.parseInt(token.toString());
			return true;
		} catch (NumberFormatException e) {
		  return false;
		} 
	}
	
	public int nextInt() throws IOException {
		if (token.length() == 0) {
			skipWhiteSpace();
		}
		String s = token.toString();
		token.setLength(0); 
		return Integer.parseInt(s);
	}
	
	public boolean hasNextLine() throws IOException {
		s = reader.readLine();
		return (s != null);
	}
  
	public String nextLine() throws IOException{
		return s;
	}
	
	/*
	public boolean hasSeparator() throws IOException {
		while (Character.isWhitespace(last) && last != '\n' && last != '\r' && last != - 1) {
			last = reader.read();
		}
		return (last == '\n' || last == '\r');
	}
	
	public boolean hasEOF() throws IOException {
		while (Character.isWhitespace(last) && last != '\n' && last != '\r' && last != - 1) {
			last = reader.read();
		}
		return (last == -1);
	}
	
	*/

	public void close() throws IOException{
		reader.close();
		inputStream.close();
	}
}