package subexpr.atomic;

import subexpr.SubExpr;

public class Const extends SubExpr {
    private boolean b;
    public Const(boolean b) {
        this.b = b;
    }

    @Override
    public SubExpr toDNF() {
        return new Const(b);
    }

    @Override
    public SubExpr toNNF(boolean negate) {
        return new Const(negate != b);
    }

    @Override
    public SubExpr stripParens(boolean wrapped) {
        return new Const(this.b);
    }

    @Override
    public String toString() {
        return this.b ? "1" : "0";
    }
}
