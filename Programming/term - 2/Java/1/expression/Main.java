package expression;

public class Main {
    public static void main(String[] args) throws Exception {
        Parser parser = new ExpressionParser();
        TripleExpression expression = parser.parse("- -654445706 - (y )/ z)" );
        System.out.println(expression.toString());
    }
}
