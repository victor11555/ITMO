package expression.generic.eval.bigInteger;

import expression.generic.TripleExpression;

import java.math.BigInteger;

public class ConstBigInt implements TripleExpression<BigInteger> {
    public final BigInteger constant;

   public ConstBigInt(String constant){
       this.constant = new BigInteger(constant);
    }

    @Override
    public BigInteger eval(BigInteger x, BigInteger y, BigInteger z) {
        return this.constant;
    }
}

