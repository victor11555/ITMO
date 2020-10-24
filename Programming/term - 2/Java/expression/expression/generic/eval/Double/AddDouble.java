package expression.generic.eval.Double;

import expression.generic.TripleExpression;

public class AddDouble implements TripleExpression<Double> {

    public TripleExpression<Double> left,right;

    public AddDouble(TripleExpression<Double> left, TripleExpression<Double> right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Double eval(Double x, Double y, Double z) {
        Double leval = this.left.eval(x, y, z);
        Double reval = this.right.eval(x, y, z);
        return leval + reval;
    }

}
