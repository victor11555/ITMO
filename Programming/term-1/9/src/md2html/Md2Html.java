package md2html;

import markup.*;
import java.io.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) throws IOException {
        StringBuilder md = new StringBuilder();
        StringBuilder html = new StringBuilder();
        Scanner scan = null;
        FileWriter writer = null;

        try {
            scan = new Scanner(new File(args[0]));
            try {
                writer = new FileWriter(new File(args[1]), StandardCharsets.UTF_8);

                String line;
                while (scan.hasNext()) {
                    line = scan.nextLine() + "\n";
                    if (line.equals("\n") || line.equals("\r")) {
                        if (md.length() > 0) {
                            Parser frame = new Parser(md);
                            TextTool paragraph = frame.parseText();
                            paragraph.toHtml(html);
                            writer.write(html.toString() + "\n");
                            md = new StringBuilder();
                            html = new StringBuilder();
                        }
                    } else {
                        md.append(line);
                    }
                }
                if (md.length() > 0) {
                    Parser frame = new Parser(md);
                    TextTool paragraph = frame.parseText();
                    paragraph.toHtml(html);
                    writer.write(html.toString() + "\n");
                    md = new StringBuilder();
                    html = new StringBuilder();
                }
                writer.close();
            } catch (NullPointerException e) {
                System.out.println("Error: no output file");
            } catch (FileNotFoundException e) {
                System.out.println("Error: no such output file found");
            } finally {
                scan.close();
            }
        } catch (NullPointerException e) {
            System.out.println("Error: no input file");
        } catch (FileNotFoundException e) {
            System.out.println("Error: no such input file found");
        }
    }
}