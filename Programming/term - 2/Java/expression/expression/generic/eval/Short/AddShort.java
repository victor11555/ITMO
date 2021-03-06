package expression.generic.eval.Short;

import expression.generic.TripleExpression;

public class AddShort implements TripleExpression<Short> {

    public TripleExpression<Short> left,right;

    public AddShort(TripleExpression<Short> left, TripleExpression<Short> right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Short eval(Short x, Short y, Short z) {
        Short leval = this.left.eval(x, y, z);
        Short reval = this.right.eval(x, y, z);
        return (short)(leval + reval);
    }

}
