import java.io.*;
import java.util.*;
import java.lang.*;

public class WordStatWords {

    public static void main(String[] args) {
		if (args.length >= 2) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));

				try {
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));

					TreeMap<String, Integer> wordToCount = new TreeMap<String, Integer>();
					while (in.ready()) {
						String line = in.readLine() + "#";
						line = line.toLowerCase();
						int start = 0;
						for (int i = 0; i < line.length(); i++) {
							if (Character.isLetter(line.charAt(i)) || Character.DASH_PUNCTUATION == Character.getType(line.charAt(i)) || line.charAt(i) == '\'') {
								start++;
							} else if (start != 0) {
								wordToCount.put(line.substring(i - start, i), wordToCount.getOrDefault(line.substring(i - start, i), 0) + 1);
								start = 0;
							}
						}
					}
					try {

						for (Map.Entry temp : wordToCount.entrySet()) {
							out.write(temp.getKey() + " " + temp.getValue().toString() + "\r\n");
						}

					} catch (FileNotFoundException e) {
						System.err.println(" output file not found: " + e.getMessage());
					} catch (IOException e) {
						System.err.println("i/o error: " + e.getMessage());
					} finally {
						out.close();
					}

				}
				finally {
					in.close();
				}
			}catch (FileNotFoundException e) {
				System.err.println("Input file not found: " + e.getMessage());
			} catch (IOException e) {
				System.err.println("I/o error: " + e.getMessage());
			}
		}
	}
}


