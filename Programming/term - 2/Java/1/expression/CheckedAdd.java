package expression;

import java.util.Objects;

public class CheckedAdd extends BinaryOperation {

    public CheckedAdd(TripleExpression left, TripleExpression right){
        super(left, right);
    }

    @Override
    public int calculate(int x, int y, int z) {
        int left = this.left.evaluate(x, y, z);//2147483644    2147483647
        int right = this.right.evaluate(x, y, z);// 6
        if(right > 0 ? left > Integer.MAX_VALUE - right : left < Integer.MIN_VALUE - right){
            throw new EvalException("overflow");
        }
        return  left + right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }

}
