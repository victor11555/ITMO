package expression.generic.eval.bigInteger;

import expression.generic.TripleExpression;

import java.math.BigInteger;

public class AddBigInt implements TripleExpression<BigInteger> {

    public TripleExpression<BigInteger> left,right;

    public AddBigInt(TripleExpression<BigInteger> left, TripleExpression<BigInteger> right){
        this.left = left;
        this.right = right;
    }

    @Override
    public BigInteger eval(BigInteger x, BigInteger y, BigInteger z) {
        return this.left.eval(x, y, z).add(this.right.eval(x, y, z));
    }
}
