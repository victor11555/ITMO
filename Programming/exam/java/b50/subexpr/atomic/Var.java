package subexpr.atomic;

import subexpr.SubExpr;
import subexpr.composite.Not;

public class Var extends SubExpr {
    private char var;
    public Var(char var) {
        this.var = var;
    }

    public SubExpr toNNF(boolean negate) {
        if (negate) {
            return new Not(new Var(this.var));
        }
        return new Var(this.var);
    }

    @Override
    public SubExpr toDNF() {
        return new Var(this.var);
    }

    @Override
    public SubExpr stripParens(boolean wrapped) {
        return new Var(this.var);
    }

    @Override
    public String toString() {
        return this.var+"";
    }
}
