package expression.generic.eval;

import expression.generic.TripleExpression;

public class Variable<R> implements TripleExpression<R> {
    private  char var;

    public Variable(char var){ this.var = var; }

    @Override
    public R eval(R x, R y, R z) {
        switch (var){
            case 'x': return x;
            case 'y': return y;
            case 'z': return z;
            default: throw new RuntimeException("Wrong variable");
        }
    }

}
