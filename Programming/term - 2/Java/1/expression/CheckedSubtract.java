package expression;

public class CheckedSubtract extends BinaryOperation {

    public CheckedSubtract(TripleExpression left, TripleExpression right){
        super(left, right);
    }


    @Override
    public int calculate(int x, int y, int z) {
        int left = this.left.evaluate(x, y, z);//2147483644    2147483647
        int right = this.right.evaluate(x, y, z);// 6
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
