package subexpr;

public abstract class SubExpr {
    public abstract SubExpr toDNF();
    public abstract SubExpr toNNF(boolean negate);
    public abstract SubExpr stripParens(boolean wrapped);
    public abstract String toString();
}
