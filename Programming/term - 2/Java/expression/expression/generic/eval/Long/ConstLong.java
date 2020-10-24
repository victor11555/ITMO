package expression.generic.eval.Long;

import expression.generic.TripleExpression;

public class ConstLong implements TripleExpression<Long> {

    public final Long constant;

   public ConstLong(String constant){
       this.constant = Long.parseLong(constant);
    }

    public Long eval(Long x, Long y, Long z){ return this.constant; }
}

