package expression.generic.eval.checked;

import expression.generic.TripleExpression;
import expression.generic.parser.ExpressionBuilder;

public class IntExprBuilder extends ExpressionBuilder<Integer> {
    @Override
    public TripleExpression<Integer> constant(String str) { return new Const(str); }

    @Override
    public TripleExpression<Integer> add(TripleExpression<Integer> left, TripleExpression<Integer> right) {
        return new CheckedAdd(left, right);
    }

    @Override
    public TripleExpression<Integer> multiply(TripleExpression<Integer> left, TripleExpression<Integer> right) {
        return new CheckedMultiply(left, right);
    }

    @Override
    public TripleExpression<Integer> subtract(TripleExpression<Integer> left, TripleExpression<Integer> right) {
        return new CheckedSubtract(left, right);
    }

    @Override
    public TripleExpression<Integer> divide(TripleExpression<Integer> left, TripleExpression<Integer> right) {
        return new CheckedDivide(left, right);
    }

    @Override
    public TripleExpression<Integer> negate(TripleExpression<Integer> one) {
        return new CheckedNegate(one);
    }

}
