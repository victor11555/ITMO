package expression.generic.eval.Short;

import expression.generic.TripleExpression;

public class NegateShort implements TripleExpression<Short> {
    public TripleExpression<Short> one;

    public NegateShort(TripleExpression<Short> one){
        this.one = one;
    }

    @Override
    public Short eval(Short x, Short y, Short z) {
        Short value = this.one.eval(x, y, z);
        return (short)-value;

    }
}
