package expression;

public class CheckedMultiply extends BinaryOperation {

    public CheckedMultiply(TripleExpression left, TripleExpression right){
        super(left, right);
    }


    @Override
    public int calculate(int x, int y, int z) {
        int left = this.left.evaluate(x, y, z);
        int right = this.right.evaluate(x, y, z);
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
