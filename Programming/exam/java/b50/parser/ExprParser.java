package parser;

import subexpr.SubExpr;
import subexpr.atomic.Const;
import subexpr.atomic.Var;
import subexpr.composite.And;
import subexpr.composite.Not;
import subexpr.composite.Or;
import subexpr.composite.Parens;

import java.io.InputStream;

public class ExprParser extends Parser {

    public ExprParser(InputStream is) {
        super(is);
    }

    public SubExpr parseSubExpr() {
        SubExpr lterm = this.parseTerm();
        this.skipBlank();
        while (this.hasNext() && this.test('&')) {
            SubExpr rterm = this.parseTerm();
            lterm = new And(lterm, rterm);
            this.skipBlank();
        }
        return lterm;
    }

    private SubExpr parseTerm() {
        SubExpr lf = this.parseFactor();
        this.skipBlank();
        while (this.hasNext() && this.test('|')) {
            SubExpr rf = this.parseFactor();
            lf = new Or(lf, rf);
            this.skipBlank();
        }
        return lf;
    }

    private SubExpr parseFactor() {
        this.skipBlank();
        if (this.test('~')) {
            return new Not(this.parseFactor());
        }
        else if (this.test('(')) {
            this.skipBlank();
            SubExpr sub = this.parseSubExpr();
            this.skipBlank();
            this.expect(")");
            return new Parens(sub);
        } else if (this.test('1')) {
            return new Const(true);
        } else if (this.test('0')) {
            return new Const(false);
        } else {
            char var = this.getCurrentChar();
            this.nextChar();
            return new Var(var);
        }
    }


}
