package subexpr.composite;

import subexpr.SubExpr;

public class And extends SubExpr {
    private final SubExpr lexpr;
    private final SubExpr rexpr;

    public And(SubExpr lexpr, SubExpr rexpr) {
        this.lexpr = lexpr;
        this.rexpr = rexpr;
    }

    @Override
    public SubExpr toNNF(boolean negate) {
        if (negate) {
            return new Parens(new Or(this.lexpr.toNNF(true), this.rexpr.toNNF(true)));
        } else {
            return new And(new Parens(this.lexpr.toNNF(false)), new Parens(this.rexpr.toNNF(false)));
        }
    }

    @Override
    public SubExpr stripParens(boolean wrapped) {
        if (wrapped) {
            return new And(this.lexpr.stripParens(false), this.rexpr.stripParens(false));
        } else {
            return new And(this.lexpr.stripParens(true), this.rexpr.stripParens(true));
        }
    }

    @Override
    public String toString() {
        return this.lexpr.toString() + "&" + this.rexpr.toString();
    }
}
