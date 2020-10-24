package expression;

import java.util.Objects;

public abstract class BinaryOperation implements TripleExpression, CommonExpression {
   protected TripleExpression left,right;

    protected BinaryOperation(TripleExpression left, TripleExpression right){
        this.left = left;
        this.right = right;
    }

    protected abstract int calculate(int x, int y, int z);
    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(x, y, z);
    }

    @Override
    public int evaluate(int x) {
        return calculate(x, 0, 0);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof BinaryOperation){
            BinaryOperation tmp = (BinaryOperation) o;
            return this.getClass().equals(tmp.getClass()) && left.equals(tmp.left) && right.equals(tmp.right);
        }
        else{
            return false;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(left.hashCode(), right.hashCode(), this.getClass());
    }
}