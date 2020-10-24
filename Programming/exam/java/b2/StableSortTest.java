
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

public class StableSortTest {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Ожидается путь до скрипта первым аргументом");
        }
        String test = args[0];
        InputStream is = null;
        try {
            File f = new File(test);
            is = new FileInputStream(f);
            StableSort ss = new StableSort(is);
            is.close();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        } finally {
            if (is != null) is.close();
        }
    }
}
