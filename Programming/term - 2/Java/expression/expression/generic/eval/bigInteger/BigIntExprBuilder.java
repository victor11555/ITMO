package expression.generic.eval.bigInteger;

import expression.generic.TripleExpression;
import expression.generic.parser.ExpressionBuilder;

import java.math.BigInteger;

public class BigIntExprBuilder extends ExpressionBuilder<BigInteger> {

    @Override
    public TripleExpression<BigInteger> constant(String str) {
        return new ConstBigInt(str);
    }

    @Override
    public TripleExpression<BigInteger> add(TripleExpression<BigInteger> left, TripleExpression<BigInteger> right) {
        return new AddBigInt(left, right);
    }

    @Override
    public TripleExpression<BigInteger> multiply(TripleExpression<BigInteger> left, TripleExpression<BigInteger> right) {
        return new MultiplyBigInt(left, right);
    }

    @Override
    public TripleExpression<BigInteger> subtract(TripleExpression<BigInteger> left, TripleExpression<BigInteger> right) {
        return new SubtractBigInt(left, right);
    }

    @Override
    public TripleExpression<BigInteger> divide(TripleExpression<BigInteger> left, TripleExpression<BigInteger> right) {
        return new DivideBigInt(left, right);
    }

    @Override
    public TripleExpression<BigInteger> negate(TripleExpression<BigInteger> one) {
        return new NegateBigInt(one);
    }




}
