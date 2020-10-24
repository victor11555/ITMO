package subexpr.composite;

import parser.Parser;
import subexpr.SubExpr;

public class And extends SubExpr {
    public final SubExpr lexpr;
    public final SubExpr rexpr;

    public And(SubExpr lexpr, SubExpr rexpr) {
        this.lexpr = lexpr;
        this.rexpr = rexpr;
    }

    @Override
    public SubExpr toDNF() {
        if (this.lexpr instanceof Parens && this.rexpr instanceof Parens) {
            if (((Parens) this.lexpr).subExpr instanceof Or && ((Parens) this.rexpr).subExpr instanceof Or) {
                SubExpr a = ((Or) ((Parens) this.lexpr).subExpr).lexpr;
                SubExpr b = ((Or) ((Parens) this.lexpr).subExpr).rexpr;
                SubExpr c = ((Or) ((Parens) this.rexpr).subExpr).lexpr;
                SubExpr d = ((Or) ((Parens) this.rexpr).subExpr).rexpr;
                Or lor = new Or(new Parens(new And(a, c)), new Parens(new And(a, d)));
                Or ror = new Or(new Parens(new And(b, c)), new Parens(new And(b, d)));
                return new Or(lor, ror).toDNF();
            }
        } else if (this.lexpr instanceof Parens && this.rexpr instanceof And) {
            if (((Parens) this.lexpr).subExpr instanceof Or) {
                SubExpr a = ((And) this.rexpr).lexpr;
                SubExpr b = ((And) this.rexpr).rexpr;
                SubExpr c = ((Or) ((Parens) this.lexpr).subExpr).lexpr;
                SubExpr d = ((Or) ((Parens) this.lexpr).subExpr).rexpr;
                And land = new And(a, new And(b, c));
                And rand = new And(a, new And(b, d));
                return new Or(new Parens(land), new Parens(rand)).toDNF();
            }
        } else if (this.lexpr instanceof And && this.rexpr instanceof Parens) {
            if (((Parens) this.rexpr).subExpr instanceof Or) {
                SubExpr a = ((And) this.lexpr).lexpr;
                SubExpr b = ((And) this.lexpr).rexpr;
                SubExpr c = ((Or) ((Parens) this.rexpr).subExpr).lexpr;
                SubExpr d = ((Or) ((Parens) this.rexpr).subExpr).rexpr;
                And land = new And(a, new And(b, c));
                And rand = new And(a, new And(b, d));
                return new Or(new Parens(land), new Parens(rand)).toDNF();
            }
        } else if (this.lexpr instanceof Parens) {
            if (((Parens) this.lexpr).subExpr instanceof Or) {
                SubExpr a = ((Or) ((Parens) this.lexpr).subExpr).lexpr;
                SubExpr b = ((Or) ((Parens) this.lexpr).subExpr).rexpr;
                SubExpr c = this.rexpr;
                And land = new And(a, c);
                And rand = new And(b, c);
                return new Or(new Parens(land), new Parens(rand)).toDNF();
            }
        } else if (this.rexpr instanceof Parens) {
            if (((Parens) this.rexpr).subExpr instanceof Or) {
                SubExpr a = ((Or) ((Parens) this.rexpr).subExpr).lexpr;
                SubExpr b = ((Or) ((Parens) this.rexpr).subExpr).rexpr;
                SubExpr c = this.lexpr;
                And land = new And(a, c);
                And rand = new And(b, c);
                return new Or(new Parens(land), new Parens(rand)).toDNF();
            }
        }
        return new And(this.lexpr.toDNF(), this.rexpr.toDNF());
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
