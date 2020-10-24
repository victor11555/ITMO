import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public final class TemplateProcessor {
    private final Map<String, String> subs = new TreeMap<>(new StringComparator());

    public TemplateProcessor(File substitutions) throws Exception {
        InputStream is = new FileInputStream(substitutions);
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.parseSub(line);
            }
        } finally {
           if (isr != null) isr.close();
        }
    }

    private void parseSub(String line) throws Exception {
        int sep = line.indexOf('=');
        if (sep == -1) {
            throw new Exception(String.format("В строке '%s' не встречается символ '='", line));
        }
        String key = line.substring(0, sep).trim();
        String value = line.substring(sep + 1).trim();
        System.out.println(key + " = " + value);
        this.subs.put(key, value);
    }

    public void process(File template) throws Exception {
        InputStream is = new FileInputStream(template);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = buf.readLine();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String contents = sb.toString();

        for (Map.Entry<String, String> entry : subs.entrySet()) {
            String var = "$" + entry.getKey();
            String sub = entry.getValue();
            System.out.println(var);
            contents = contents.replaceAll(Pattern.quote(var), sub);
        }

        FileWriter fw = new FileWriter(template);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(contents);
        writer.close();
    }

    private static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.length() - o2.length();
        }
    }
}