package expression.generic;
public interface TripleExpression<R> {
    R eval(R x, R y, R z);
}
