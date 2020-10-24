package expression;

import java.util.Objects;

public class Variable implements TripleExpression, Expression {
    String str;
    public Variable(String str){
        this.str = str;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toMiniString() {
        return "";
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (str){
            case "x": return x;
            case "y": return y;
            case "z": return z;
            default: throw new RuntimeException("Wrong variable");
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Variable){
            Variable tmp = (Variable) o;
            return this.str.equals(tmp.str);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return str.hashCode();
    }
}
