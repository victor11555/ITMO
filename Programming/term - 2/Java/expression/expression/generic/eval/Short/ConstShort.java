package expression.generic.eval.Short;

import expression.generic.TripleExpression;

public class ConstShort implements TripleExpression<Short> {

    public final Short constant;

   public ConstShort(String constant){
       this.constant = Short.parseShort(constant);
    }

    public Short eval(Short x, Short y, Short z){ return this.constant; }
}

