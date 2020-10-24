package expression;

public class Main {
    public static void main(String[] args) {
        Parser parser = new ExpressionParser();
        TripleExpression expression = parser.parse("-(-(-		-5 + 16   *x*y) + 1 * z) -(((-11)))" );
        System.out.println(expression.toString());
    }
}
