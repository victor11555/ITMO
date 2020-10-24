package expression.generic.eval.checked;

import expression.generic.eval.EvalException;
import expression.generic.TripleExpression;

public class CheckedDivide implements TripleExpression<Integer>{
    TripleExpression<Integer> left, right;

    public CheckedDivide(TripleExpression<Integer> left, TripleExpression<Integer> right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer eval(Integer x, Integer y, Integer z) {
        int right = this.right.eval(x, y, z);
        int left = this.left.eval(x, y, z);
        if(right == 0){
            throw new EvalException("Division by zero");
        }
        if(left == Integer.MIN_VALUE && right == -1){
            throw new EvalException("overflow");
        }
       return  left / right;
    }
    @Override
    public String toString() {
        return "(" + left.toString() + " / " + right.toString() + ")";
    }
}
