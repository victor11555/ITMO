package expression;

public class Subtract extends BinaryOperation {

    public Subtract(TripleExpression left, TripleExpression right){
        super(left, right);
    }


    @Override
    public int calculate(int x, int y, int z) {
        return left.evaluate(x, y, z) - right.evaluate(x, y, z);
    }
    @Override
    public String toString() {
        return "(" + left.toString() + " - " + right.toString() + ")";
    }
}
