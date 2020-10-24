package expression.generic;
import expression.generic.parser.ParseException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser<R> {
    TripleExpression<R> parse(String expression) throws ParseException;
}