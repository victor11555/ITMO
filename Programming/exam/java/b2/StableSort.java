

import java.io.*;
import java.util.*;

public final class StableSort {

    private final Map<Long, List<Entry>> storage = new TreeMap<>();

    public StableSort(InputStream is) throws Exception {
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.interpret(line);
            }
        } finally {
           if (isr != null) isr.close();
        }
    }

    private void interpret(String str) throws Exception {
        String[] parts = str.split("\\s");
        if (parts.length < 1) {
            String errMsg = String.format("Строка '%s' не содержит команду"
                    , str
            );
            throw new Exception(errMsg);
        }
        String command = parts[0].toLowerCase();
        if (command.equals("add")) {
            if (parts.length != 3) {
                String errMsg = String.format("Некорректное кол-во параметров для команды '%s' в строке '%s'"
                        , command
                        , str
                );
                throw new Exception(errMsg);
            }
            this.addEntry(parts[1], parts[2]);
        } else if (command.equals("remove")) {
            if (parts.length != 2) {
                String errMsg = String.format("Некорректное кол-во параметров для команды '%s' в строке '%s'"
                        , command
                        , str
                );
                throw new Exception(errMsg);
            }
            this.removeEntry(parts[1]);
        } else if (command.equals("print")) {
            if (parts.length != 2) {
                String errMsg = String.format("Некорректное кол-во параметров для команды '%s' в строке '%s'"
                        , command
                        , str
                );
                throw new Exception(errMsg);
            }
            this.printToFile(parts[1]);
        } else {
            String errMsg = String.format("Команда '%s' в строке '%s' неизвестна"
                    , command
                    , str
            );
            throw new Exception(errMsg);
        }
    }

    private void addEntry(String ixStr, String str) throws Exception {
        try {
            long ix = Long.parseLong(ixStr);
            Entry entry = new Entry(ix, str);
            List<Entry> list = this.storage.get(ix);
            if (list == null) {
                list = new ArrayList<>();
                this.storage.put(ix, list);
            }
            list.add(entry);
            this.storage.put(ix, list);
            System.out.println("added " + entry.toString());
        } catch (NumberFormatException e) {
            throw new Exception(String.format("'%s - не число'", ixStr));
        }
    }

    private void removeEntry(String ixStr) throws Exception {
        try {
            long ix = Long.parseLong(ixStr);
            this.storage.remove(ix);
            System.out.println("removed entries with ix " + ixStr);
        } catch (NumberFormatException e) {
            throw new Exception(String.format("'%s - не число'", ixStr));
        }
    }

    private void printToFile(String filePath) throws Exception {
        File f = new File(filePath);
        if (!f.exists()) {
            if (!f.createNewFile()) {
                String errMsg = String.format("Файла по пути '%s' не может быть создан"
                        , filePath
                );
                throw new Exception(errMsg);
            }
        }
        if (!f.canWrite()) {
            String errMsg = String.format("Файл по пути '%s' не доступен для записи"
                    , filePath
            );
            throw new Exception(errMsg);
        }
        FileWriter fw = new FileWriter(f);

        for (List<Entry> list : this.storage.values()) {
            for (Entry e : list) {
                fw.append(e.toString()).append('\n');
            }
        }
        fw.close();
        System.out.println("printed to "+filePath);
    }

    public static final class Entry {
        public final long ix;
        public final String str;

        Entry(long ix, String str) {
            this.ix = ix;
            this.str = str;
        }

        @Override
        public String toString() {
            return String.format("%d %s", this.ix, this.str);
        }
    }
}
