package expression.generic.eval.checked;

import expression.generic.eval.EvalException;
import expression.generic.TripleExpression;

public class CheckedSubtract implements TripleExpression<Integer> {
    TripleExpression<Integer> left, right;

    public CheckedSubtract(TripleExpression<Integer> left, TripleExpression<Integer> right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public Integer eval(Integer x, Integer y, Integer z) {
        int left = this.left.eval(x, y, z);//2147483644    2147483647
        int right = this.right.eval(x, y, z);// 6
        if(right > 0 ? left < Integer.MIN_VALUE + right : left > Integer.MAX_VALUE + right){
            throw new EvalException("overflow");
        }
        return  left - right;
    }
    @Override
    public String toString() {
        return "(" + left.toString() + " - " + right.toString() + ")";
    }
}
