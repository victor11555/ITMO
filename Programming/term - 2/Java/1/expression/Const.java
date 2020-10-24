package expression;

import java.util.Objects;

public class Const implements TripleExpression, CommonExpression {
    int one;
    public Const(int one){
        this.one = one;
    }

    @Override
    public int evaluate(int x) {
        return one;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return one;
    }

    @Override
    public String toMiniString() {
        return "";
    }

    @Override
    public String toString() {
        return one + "";
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Const){
            Const tmp = (Const) o;
            return this.one == tmp.one;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(one);
    }
}
