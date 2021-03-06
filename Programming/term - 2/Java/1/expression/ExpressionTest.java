package expression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ExpressionTest extends BaseTest {
    private final List<ToMiniString> prev = new ArrayList<>();
    private final boolean checkMini;

    protected ExpressionTest(final boolean checkMini) {
        this.checkMini = checkMini;
    }

    @Override
    protected void test() {
        handmade();
        generated();
    }

    private void handmade() {
        testExpression("10", "10", new Const(10), x -> 10);
        testExpression("x", "x", new Variable("x"), x -> x);
        testExpression("(x + 2)", "x + 2", new CheckedAdd(new Variable("x"), new Const(2)), x -> x + 2);
        testExpression("(2 - x)", "2 - x", new CheckedSubtract(new Const(2), new Variable("x")), x -> 2 - x);
        testExpression("(3 * x)", "3 * x", new CheckedMultiply(new Const(3), new Variable("x")), x -> 3*x);
        testExpression("(x + x)", "x + x", new CheckedAdd(new Variable("x"), new Variable("x")), x -> x + x);
        testExpression("(x / -2)", "x / -2", new CheckedDivide(new Variable("x"), new Const(-2)), x -> -x / 2);
        testExpression("(x + x)", "x + x", new CheckedAdd(new Variable("x"), new Variable("x")), x -> x + x);
        testExpression("(2 + x)", "2 + x", new CheckedAdd(new Const(2), new Variable("x")), x -> 2 + x);
        testExpression("(x + 2)", "x + 2", new CheckedAdd(new Variable("x"), new Const(2)), x -> x + 2);
        testExpression("((1 + 2) + 3)", "1 + 2 + 3", new CheckedAdd(new CheckedAdd(new Const(1), new Const(2)), new Const(3)), x -> 6);
        testExpression("(1 + (2 + 3))", "1 + 2 + 3", new CheckedAdd(new Const(1), new CheckedAdd(new Const(2), new Const(3))), x -> 6);
        testExpression("((1 - 2) - 3)", "1 - 2 - 3", new CheckedSubtract(new CheckedSubtract(new Const(1), new Const(2)), new Const(3)), x -> -4);
        testExpression("(1 - (2 - 3))", "1 - (2 - 3)", new CheckedSubtract(new Const(1), new CheckedSubtract(new Const(2), new Const(3))), x -> 2);
        testExpression("((1 * 2) * 3)", "1 * 2 * 3", new CheckedMultiply(new CheckedMultiply(new Const(1), new Const(2)), new Const(3)), x -> 6);
        testExpression("(1 * (2 * 3))", "1 * 2 * 3", new CheckedMultiply(new Const(1), new CheckedMultiply(new Const(2), new Const(3))), x -> 6);
        testExpression("((10 / 2) / 3)", "10 / 2 / 3", new CheckedDivide(new CheckedDivide(new Const(10), new Const(2)), new Const(3)), x -> 10 / 2 / 3);
        testExpression("(10 / (3 / 2))", "10 / (3 / 2)", new CheckedDivide(new Const(10), new CheckedDivide(new Const(3), new Const(2))), x -> 10);
        testExpression(
                "((x * x) + ((x - 1) / 10))",
                "x * x + (x - 1) / 10",
                new CheckedAdd(
                        new CheckedMultiply(new Variable("x"), new Variable("x")),
                        new CheckedDivide(new CheckedSubtract(new Variable("x"), new Const(1)), new Const(10))
                ),
                x -> x * x + (x - 1) / 10
        );
        testExpression("(x * -1000000000)", "x * -1000000000", new CheckedMultiply(new Variable("x"), new Const(-1_000_000_000)), x -> x * -1_000_000_000);
        testExpression("(10 / x)", "10 / x", new CheckedDivide(new Const(10), new Variable("x")), x -> 10 / x);
        //noinspection PointlessArithmeticExpression
        testExpression("(x / x)", "x / x", new CheckedDivide(new Variable("x"), new Variable("x")), x -> x / x);
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    private void generated() {
        final Variable vx = new Variable("x");
        final Const c1 = new Const(1);
        final Const c2 = new Const(2);

        testExpression("(2 + 1)", "2 + 1", new CheckedAdd(c2, c1), x -> 2 + 1);
        testExpression("(x - 1)", "x - 1", new CheckedSubtract(vx, c1), x -> x - 1);
        testExpression("(1 * 2)", "1 * 2", new CheckedMultiply(c1, c2), x -> 1 * 2);
        testExpression("(x / 1)", "x / 1", new CheckedDivide(vx, c1), x -> x / 1);
        testExpression("(1 + (2 + 1))", "1 + 2 + 1", new CheckedAdd(c1, new CheckedAdd(c2, c1)), x -> 1 + 2 + 1);
        testExpression("(x - (x - 1))", "x - (x - 1)", new CheckedSubtract(vx, new CheckedSubtract(vx, c1)), x -> x - (x - 1));
        testExpression("(2 * (x / 1))", "2 * (x / 1)", new CheckedMultiply(c2, new CheckedDivide(vx, c1)), x -> 2 * (x / 1));
        testExpression("(2 / (x - 1))", "2 / (x - 1)", new CheckedDivide(c2, new CheckedSubtract(vx, c1)), x -> 2 / (x - 1));
        testExpression("((1 * 2) + x)", "1 * 2 + x", new CheckedAdd(new CheckedMultiply(c1, c2), vx), x -> 1 * 2 + x);
        testExpression("((x - 1) - 2)", "x - 1 - 2", new CheckedSubtract(new CheckedSubtract(vx, c1), c2), x -> x - 1 - 2);
        testExpression("((x / 1) * 2)", "x / 1 * 2", new CheckedMultiply(new CheckedDivide(vx, c1), c2), x -> x / 1 * 2);
        testExpression("((2 + 1) / 1)", "(2 + 1) / 1", new CheckedDivide(new CheckedAdd(c2, c1), c1), x -> (2 + 1) / 1);
        testExpression("(1 + (1 + (2 + 1)))", "1 + 1 + 2 + 1", new CheckedAdd(c1, new CheckedAdd(c1, new CheckedAdd(c2, c1))), x -> 1 + 1 + 2 + 1);
        testExpression("(x - ((1 * 2) + x))", "x - (1 * 2 + x)", new CheckedSubtract(vx, new CheckedAdd(new CheckedMultiply(c1, c2), vx)), x -> x - (1 * 2 + x));
        testExpression("(x * (2 / (x - 1)))", "x * (2 / (x - 1))", new CheckedMultiply(vx, new CheckedDivide(c2, new CheckedSubtract(vx, c1))), x -> x * (2 / (x - 1)));
        testExpression("(x / (1 + (2 + 1)))", "x / (1 + 2 + 1)", new CheckedDivide(vx, new CheckedAdd(c1, new CheckedAdd(c2, c1))), x -> x / (1 + 2 + 1));
        testExpression("((1 * 2) + (2 + 1))", "1 * 2 + 2 + 1", new CheckedAdd(new CheckedMultiply(c1, c2), new CheckedAdd(c2, c1)), x -> 1 * 2 + 2 + 1);
        testExpression("((2 + 1) - (2 + 1))", "2 + 1 - (2 + 1)", new CheckedSubtract(new CheckedAdd(c2, c1), new CheckedAdd(c2, c1)), x -> 2 + 1 - (2 + 1));
        testExpression("((x - 1) * (x / 1))", "(x - 1) * (x / 1)", new CheckedMultiply(new CheckedSubtract(vx, c1), new CheckedDivide(vx, c1)), x -> (x - 1) * (x / 1));
        testExpression("((x - 1) / (1 * 2))", "(x - 1) / (1 * 2)", new CheckedDivide(new CheckedSubtract(vx, c1), new CheckedMultiply(c1, c2)), x -> (x - 1) / (1 * 2));
        testExpression("(((x - 1) - 2) + x)", "x - 1 - 2 + x", new CheckedAdd(new CheckedSubtract(new CheckedSubtract(vx, c1), c2), vx), x -> x - 1 - 2 + x);
        testExpression("(((1 * 2) + x) - 1)", "1 * 2 + x - 1", new CheckedSubtract(new CheckedAdd(new CheckedMultiply(c1, c2), vx), c1), x -> 1 * 2 + x - 1);
        testExpression("(((2 + 1) / 1) * x)", "(2 + 1) / 1 * x", new CheckedMultiply(new CheckedDivide(new CheckedAdd(c2, c1), c1), vx), x -> (2 + 1) / 1 * x);
        testExpression("((2 / (x - 1)) / 2)", "2 / (x - 1) / 2", new CheckedDivide(new CheckedDivide(c2, new CheckedSubtract(vx, c1)), c2), x -> 2 / (x - 1) / 2);
    }

    private void testExpression(final String full, final String mini, final CommonExpression actual, final CommonExpression expected) {
        System.out.println("Testing " + full);
        for (int i = 0; i < 10; i++) {
            counter.nextTest();
            assertEquals(String.format("f(%d)", i), evaluate(expected, i), evaluate(actual, i));
            counter.passed();
        }
        checkEqualsAndToString(full, mini, actual);
    }

    protected void checkEqualsAndToString(final String full, final String mini, final ToMiniString expression) {
        final String actualToString = expression.toString();
        checkToString("toString", full, actualToString);
        if (checkMini) {
            checkToString("toMiniString", mini, expression.toMiniString());
        }

        counter.nextTest();
        assertTrue("Equals to this", expression.equals(expression));
        assertTrue("Equals to null", !expression.equals(null));
        counter.passed();

        for (Object prev : prev) {
            counter.nextTest();
            final String prevToString = prev.toString();
            final boolean equals = prevToString.equals(actualToString);
            assertTrue("Equals to " + prevToString, prev.equals(expression) == equals);
            assertTrue("Equals to " + prevToString, expression.equals(prev) == equals);
            assertTrue("Inconsistent hashCode " + prevToString, (prev.hashCode() == expression.hashCode()) == equals);
            counter.passed();
        }

        prev.add(expression);
    }

    private void checkToString(final String method, final String expected, final String actual) {
        counter.nextTest();
        assertTrue(String.format("Invalid %s\n     expected: %s\n       actual: %s", method, expected, actual), expected.equals(actual));
        counter.passed();
    }

    private static Integer evaluate(final CommonExpression commonExpression, final int x) {
        try {
            return commonExpression.evaluate(x);
        } catch (final ArithmeticException e) {
            return null;
        }
    }

    public static void main(final String[] args) {
        new ExpressionTest(mode(args)).run();
    }

    protected static boolean mode(final String[] args) {
        return mode(args, "easy", "hard") == 1;
    }
}
