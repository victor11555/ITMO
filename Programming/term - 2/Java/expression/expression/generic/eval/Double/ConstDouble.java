package expression.generic.eval.Double;

import expression.generic.TripleExpression;

public class ConstDouble implements TripleExpression<Double> {

    public final Double constant;

   public ConstDouble(String constant){
       this.constant = Double.parseDouble(constant);
    }

    public Double eval(Double x, Double y, Double z){ return this.constant; }
}

