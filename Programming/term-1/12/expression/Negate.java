package expression;

public class Negate  implements TripleExpression, CommonExpression {
    TripleExpression expression;

    public Negate(TripleExpression expression){
        this.expression = expression;
    }

    @Override
    public int evaluate(int x) {
        return -expression.evaluate(x, 0, 0);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -expression.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-(" + expression.toString() + ")";
    }
}
