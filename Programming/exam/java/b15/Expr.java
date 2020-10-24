import parser.ExprParser;
import subexpr.SubExpr;
import java.io.InputStream;

public class Expr {
    public SubExpr subExpr;

    public Expr(InputStream is) {
        this(new ExprParser(is).parseSubExpr());
    }

    private Expr(SubExpr subExpr) {
        this.subExpr = subExpr;
    }

    public Expr toNNF() {
        return new Expr(subExpr.toNNF(false).stripParens(false));
    }

    @Override
    public String toString() {
        return this.subExpr.toString();
    }
}
