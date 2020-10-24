package expression.generic.parser;

import expression.generic.TripleExpression;
import expression.generic.eval.Variable;

public abstract class ExpressionBuilder<R>  {

    public final TripleExpression<R> variable(char ch){
        return new Variable<R>(ch);
    }

    public abstract TripleExpression<R> constant(String str) throws ParseException;
    public abstract TripleExpression<R> negate(TripleExpression<R> one);
    public abstract TripleExpression<R> add(TripleExpression<R> left, TripleExpression<R> right);
    public abstract TripleExpression<R> divide(TripleExpression<R> left, TripleExpression<R> right);
    public abstract TripleExpression<R> subtract(TripleExpression<R> left, TripleExpression<R> right);
    public abstract TripleExpression<R> multiply(TripleExpression<R> left, TripleExpression<R> right);

}
