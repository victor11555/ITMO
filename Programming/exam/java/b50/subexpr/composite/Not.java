package subexpr.composite;

import subexpr.SubExpr;

public class Not extends SubExpr {

    public final SubExpr subExpr;

    public Not(SubExpr sub) {
        this.subExpr = sub;
    }

    @Override
    public SubExpr toDNF() {
        return new Not(subExpr.toDNF());
    }

    @Override
    public SubExpr toNNF(boolean negate) {
        return this.subExpr.toNNF(!negate);
    }

    @Override
    public SubExpr stripParens(boolean wrapped) {
        return new Not(this.subExpr.stripParens(false));
    }

    @Override
    public String toString() {
        return "~"+this.subExpr.toString();
    }
}
