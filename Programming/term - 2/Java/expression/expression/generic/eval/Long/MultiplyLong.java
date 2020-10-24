package expression.generic.eval.Long;

import expression.generic.TripleExpression;

public class MultiplyLong implements TripleExpression<Long> {

    public TripleExpression<Long> left,right;

    public MultiplyLong(TripleExpression<Long> left, TripleExpression<Long> right){
        this.left = left;
        this.right = right;
    }

    @Override
    public Long eval(Long x, Long y, Long z) {
        Long leval = this.left.eval(x, y, z);
        Long reval = this.right.eval(x, y, z);
        return leval * reval;
    }

}