package expression;

public class CheckedDivide extends BinaryOperation {

    public CheckedDivide(TripleExpression left, TripleExpression right){
        super(left, right);
    }

    @Override
    public int calculate(int x, int y, int z) {
        int right = this.right.evaluate(x, y, z);
        int left = this.left.evaluate(x, y, z);
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
