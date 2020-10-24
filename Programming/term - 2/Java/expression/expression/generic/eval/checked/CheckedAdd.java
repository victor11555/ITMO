package expression.generic.eval.checked;

import expression.generic.eval.EvalException;
import expression.generic.TripleExpression;

public class CheckedAdd implements TripleExpression<Integer> {
    TripleExpression<Integer> left, right;

    public CheckedAdd(TripleExpression<Integer> left, TripleExpression<Integer> right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer eval(Integer x, Integer y, Integer z) {
        int left = this.left.eval(x, y, z);
        int right = this.right.eval(x, y, z);
        if(left > 0 ? right > Integer.MAX_VALUE - left : right < Integer.MIN_VALUE - left){
            throw new EvalException("overflow" );
        }
        return  left + right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }

}
