package expression.generic.eval.bigInteger;

import expression.generic.TripleExpression;

import java.math.BigInteger;

public class NegateBigInt implements TripleExpression<BigInteger> {

    public TripleExpression<BigInteger> one;

    public NegateBigInt(TripleExpression<BigInteger> one){
        this.one = one;
    }

    @Override
    public BigInteger eval(BigInteger x, BigInteger y, BigInteger z) {
        return this.one.eval(x, y, z).negate();
    }
}
