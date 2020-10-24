package expression.generic.eval.Double;

import expression.generic.TripleExpression;

public class NegateDouble implements TripleExpression<Double> {
    public TripleExpression<Double> one;

    public NegateDouble(TripleExpression<Double> one){
        this.one = one;
    }

    @Override
    public Double eval(Double x, Double y, Double z) {
        Double value = this.one.eval(x, y, z);
        return -value;

    }
}
