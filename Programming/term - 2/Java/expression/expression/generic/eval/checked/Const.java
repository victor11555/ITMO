package expression.generic.eval.checked;

import expression.generic.eval.EvalException;
import expression.generic.TripleExpression;

public class Const implements TripleExpression<Integer> {
    int one;
    public Const(String one){
        try {
            this.one = Integer.parseInt(one);
        }catch (Exception e){
            throw new EvalException("Wrong const");
        }
    }

    @Override
    public Integer eval(Integer x, Integer y, Integer z) {
        return one;
    }

}
