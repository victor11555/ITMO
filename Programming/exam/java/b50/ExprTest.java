import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ExprTest {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Ожидается формула первым аргументом");
        }
        try (InputStream is = new ByteArrayInputStream(args[0].getBytes())) {
            Expr expr = new Expr(is);
            Expr dnfexpr = expr.toDNF();
            System.out.println(expr.toString());
            System.out.println(dnfexpr.toString());
        }
    }
}
