package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface CommonExpression extends ToMiniString {
    int evaluate(int x);
}
