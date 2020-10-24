package expression;

public class CheckedNegate implements TripleExpression, CommonExpression {
    TripleExpression expression;

    public CheckedNegate(TripleExpression expression){
        this.expression = expression;
    }

    @Override
    public int evaluate(int x) {
        return this.evaluate(x, 0, 0);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int tmp = expression.evaluate(x, y, z);
        if(tmp == Integer.MIN_VALUE ){
            throw new EvalException("overflow");
        }
        return -tmp;
    }

    @Override
    public String toString() {
        return "-(" + expression.toString() + ")";
    }
}
