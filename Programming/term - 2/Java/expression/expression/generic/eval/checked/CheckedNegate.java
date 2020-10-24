package expression.generic.eval.checked;

import expression.generic.eval.EvalException;
import expression.generic.TripleExpression;

public class CheckedNegate implements TripleExpression<Integer> {
    TripleExpression<Integer> expression;

    public CheckedNegate(TripleExpression<Integer> expression){
        this.expression = expression;
    }

    @Override
    public Integer eval(Integer x, Integer y, Integer z) {
        int tmp = expression.eval(x, y, z);
        if(tmp == Integer.MIN_VALUE ){
            throw new EvalException("overflow");
        }
        return -tmp;
    }

    @Override
    public String toString() {
        return "-(" + expression.toString() + ")";
    }
}
