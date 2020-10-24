package expression.generic.eval.Double;

import expression.generic.TripleExpression;
import expression.generic.parser.ExpressionBuilder;

public class DoubleExprBuilder extends ExpressionBuilder<Double> {

    @Override
    public TripleExpression<Double> constant(String str) { return new ConstDouble(str); }

    @Override
    public TripleExpression<Double> add(TripleExpression<Double> left, TripleExpression<Double> right) {
        return new AddDouble(left, right);
    }

    @Override
    public TripleExpression<Double> multiply(TripleExpression<Double> left, TripleExpression<Double> right) {
        return new MultiplyDouble(left, right);
    }

    @Override
    public TripleExpression<Double> subtract(TripleExpression<Double> left, TripleExpression<Double> right) {
        return new SubtractDouble(left, right);
    }

    @Override
    public TripleExpression<Double> divide(TripleExpression<Double> left, TripleExpression<Double> right) {
        return new DivideDouble(left, right);
    }

    @Override
    public TripleExpression<Double> negate(TripleExpression<Double> one) {
        return new NegateDouble(one);
    }



}
