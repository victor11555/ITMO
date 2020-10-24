import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class BBCodeTest {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Ожидается путь до файла первым аргументом");
        }

        try (InputStream is = new FileInputStream(new File(args[0]))) {
            BBCode bbCode = new BBCode(is);
            System.out.println(bbCode.html());
        }
    }
}
