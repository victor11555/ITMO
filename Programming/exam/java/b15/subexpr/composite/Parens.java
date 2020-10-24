package subexpr.composite;

import subexpr.SubExpr;

public class Parens extends SubExpr {
    private final SubExpr expr;
    public Parens(SubExpr expr) {
        this.expr = expr;
    }

    @Override
    public SubExpr toNNF(boolean negate) {
        return new Parens(this.expr.toNNF(negate));
    }

    public SubExpr stripParens(boolean wrapped) {
        return this.expr.stripParens(!wrapped);
    }

    @Override
    public String toString() {
        return "("+this.expr.toString()+")";
    }
}
