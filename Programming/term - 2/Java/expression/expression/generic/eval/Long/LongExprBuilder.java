package expression.generic.eval.Long;

import expression.generic.TripleExpression;
import expression.generic.parser.ExpressionBuilder;

public class LongExprBuilder extends ExpressionBuilder<Long> {

    @Override
    public TripleExpression<Long> constant(String str) { return new ConstLong(str); }

    @Override
    public TripleExpression<Long> add(TripleExpression<Long> left, TripleExpression<Long> right) {
        return new AddLong(left, right);
    }

    @Override
    public TripleExpression<Long> multiply(TripleExpression<Long> left, TripleExpression<Long> right) {
        return new MultiplyLong(left, right);
    }

    @Override
    public TripleExpression<Long> subtract(TripleExpression<Long> left, TripleExpression<Long> right) {
        return new SubtractLong(left, right);
    }

    @Override
    public TripleExpression<Long> divide(TripleExpression<Long> left, TripleExpression<Long> right) {
        return new DivideLong(left, right);
    }

    @Override
    public TripleExpression<Long> negate(TripleExpression<Long> one) {
        return new NegateLong(one);
    }



}
