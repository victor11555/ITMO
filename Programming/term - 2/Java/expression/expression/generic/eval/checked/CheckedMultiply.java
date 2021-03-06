package expression.generic.eval.checked;

import expression.generic.eval.EvalException;
import expression.generic.TripleExpression;

public class CheckedMultiply implements TripleExpression<Integer> {

    TripleExpression<Integer> left, right;

    public CheckedMultiply(TripleExpression<Integer> left, TripleExpression<Integer> right){
        this.left = left;
        this.right = right;
    }


    @Override
    public Integer eval(Integer x, Integer y, Integer z) {
        int left = this.left.eval(x, y, z);
        int right = this.right.eval(x, y, z);
        if(right > 0 ){
            if(left > Integer.MAX_VALUE / right || left < Integer.MIN_VALUE / right){
                throw new EvalException("overflow");
            }
        }
        if(right < -1) {
            if(left < Integer.MAX_VALUE / right || left > Integer.MIN_VALUE / right){
                throw new EvalException("overflow");
            }
        }
        if (right == -1 && left == Integer.MIN_VALUE ) {
            throw new EvalException("overflow");
        }
        return left * right;
    }
    @Override
    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }
}
