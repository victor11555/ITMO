package expression.generic.eval.Long;

import expression.generic.TripleExpression;

public class NegateLong implements TripleExpression<Long> {
    public TripleExpression<Long> one;

    public NegateLong(TripleExpression<Long> one){
        this.one = one;
    }

    @Override
    public Long eval(Long x, Long y, Long z) {
        Long value = this.one.eval(x, y, z);
        return -value;

    }
}
