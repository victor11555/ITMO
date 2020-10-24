package expression.generic.eval;

import expression.generic.TripleExpression;

public abstract class BinaryOperation<R> implements TripleExpression<R> {
   protected TripleExpression<R> left,right;

    protected BinaryOperation(TripleExpression<R> left, TripleExpression<R> right){
        this.left = left;
        this.right = right;
    }

    protected abstract R calculate(R x, R y, R z);

    @Override
    public R eval(R x, R y, R z) {
        return calculate(x, y, z);
    }

}