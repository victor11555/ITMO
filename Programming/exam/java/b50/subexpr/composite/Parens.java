package subexpr.composite;

import subexpr.SubExpr;

public class Parens extends SubExpr {
    public final SubExpr subExpr;
    public Parens(SubExpr expr) {
        this.subExpr = expr;
    }

    @Override
    public SubExpr toDNF() {
        return new Parens(subExpr.toDNF());
    }

    @Override
    public SubExpr toNNF(boolean negate) {
        return new Parens(this.subExpr.toNNF(negate));
    }

    @Override
    public SubExpr stripParens(boolean wrapped) {
        return this.subExpr.stripParens(!wrapped);
    }

    @Override
    public String toString() {
        return "("+this.subExpr.toString()+")";
    }
}
