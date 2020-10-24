package expression.generic.eval.Short;

import expression.generic.TripleExpression;
import expression.generic.parser.ExpressionBuilder;

public class ShortExprBuilder extends ExpressionBuilder<Short> {

    @Override
    public TripleExpression<Short> constant(String str) { return new ConstShort(str); }

    @Override
    public TripleExpression<Short> add(TripleExpression<Short> left, TripleExpression<Short> right) {
        return new AddShort(left, right);
    }

    @Override
    public TripleExpression<Short> multiply(TripleExpression<Short> left, TripleExpression<Short> right) {
        return new MultiplyShort(left, right);
    }

    @Override
    public TripleExpression<Short> subtract(TripleExpression<Short> left, TripleExpression<Short> right) {
        return new SubtractShort(left, right);
    }

    @Override
    public TripleExpression<Short> divide(TripleExpression<Short> left, TripleExpression<Short> right) {
        return new DivideShort(left, right);
    }

    @Override
    public TripleExpression<Short> negate(TripleExpression<Short> one) {
        return new NegateShort(one);
    }



}
