package expression.generic;

import expression.generic.eval.Double.DoubleExprBuilder;
import expression.generic.eval.Long.LongExprBuilder;
import expression.generic.eval.Short.ShortExprBuilder;
import expression.generic.eval.bigInteger.BigIntExprBuilder;
import expression.generic.eval.checked.IntExprBuilder;
import expression.generic.parser.ExpressionBuilder;
import expression.generic.parser.ExpressionParser;
import expression.generic.parser.ParseException;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {

        switch (mode) {
            case "i": {
                return checkedTabulate(expression, x1, x2, y1, y2, z1, z2);
            }
            case "d": {
                return doubleTabulate(expression, x1, x2, y1, y2, z1, z2);
            }
            case "bi": {
                return bigTabulate(expression, x1, x2, y1, y2, z1, z2);
            }
            case "l": {
                return longTabulate(expression, x1, x2, y1, y2, z1, z2);
            }
            case "s": {
                return shortTabulate(expression, x1, x2, y1, y2, z1, z2);
            }
            default:
                throw new RuntimeException("Unknown mode");
        }

    }

    public Double[][][] doubleTabulate(String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        ExpressionBuilder<Double> expressionBuilder = new DoubleExprBuilder();
        ExpressionParser<Double> parser = new ExpressionParser<Double>(expressionBuilder);
        TripleExpression<Double> expr = parser.parse(expression);
        int x = x2 - x1 + 1;
        int y = y2 - y1 + 1;
        int z = z2 - z1 + 1;
        Double[][][] table = new Double[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    table[i][j][k] = expr.eval((double) x1 + i, (double) y1 + j, (double) z1 + k);
                }
            }
        }
        return table;
    }

    public Long[][][] longTabulate(String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        ExpressionBuilder<Long> expressionBuilder = new LongExprBuilder();
        ExpressionParser<Long> parser = new ExpressionParser<Long>(expressionBuilder);
        TripleExpression<Long> expr = parser.parse(expression);
        int x = x2 - x1 + 1;
        int y = y2 - y1 + 1;
        int z = z2 - z1 + 1;
        Long[][][] table = new Long[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    try {
                        table[i][j][k] = expr.eval((long) x1 + i, (long) y1 + j, (long) z1 + k);
                    } catch (Exception e) {
                        table[i][j][k] = null;
                    }
                }
            }
        }
        return table;
    }

    public Short[][][] shortTabulate(String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        ExpressionBuilder<Short> expressionBuilder = new ShortExprBuilder();
        ExpressionParser<Short> parser = new ExpressionParser<Short>(expressionBuilder);
        TripleExpression<Short> expr = parser.parse(expression);
        int x = x2 - x1 + 1;
        int y = y2 - y1 + 1;
        int z = z2 - z1 + 1;
        Short[][][] table = new Short[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    try {
                        table[i][j][k] = expr.eval((short) (x1 + i), (short) (y1 + j), (short) (z1 + k));
                    } catch (Exception e) {
                        table[i][j][k] = null;
                    }
                }
            }
        }
        return table;
    }

    public Integer[][][] checkedTabulate(String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        ExpressionBuilder<Integer> expressionBuilder = new IntExprBuilder();
        ExpressionParser<Integer> parser = new ExpressionParser<Integer>(expressionBuilder);
        TripleExpression<Integer> expr = parser.parse(expression);
        int x = x2 - x1;
        int y = y2 - y1;
        int z = z2 - z1;
        Integer[][][] table = new Integer[x + 1][y + 1][z + 1];
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                for (int k = 0; k <= z; k++) {
                    try {
                        table[i][j][k] = expr.eval(x1 + i, y1 + j, z1 + k);
                    } catch (Exception e) {
                        table[i][j][k] = null;
                    }
                }
            }
        }
        return table;
    }

    public BigInteger[][][] bigTabulate(String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        ExpressionBuilder<BigInteger> expressionBuilder = new BigIntExprBuilder();
        ExpressionParser<BigInteger> parser = new ExpressionParser<BigInteger>(expressionBuilder);
        TripleExpression<BigInteger> expr = parser.parse(expression);
        int x = x2 - x1 + 1;
        int y = y2 - y1 + 1;
        int z = z2 - z1 + 1;
        BigInteger[][][] table = new BigInteger[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    try {
                        table[i][j][k] = expr.eval(BigInteger.valueOf(x1 + i), BigInteger.valueOf(y1 + j), BigInteger.valueOf(z1 + k));
                    } catch (Exception e) {
                        table[i][j][k] = null;
                    }
                }
            }
        }
        return table;
    }
}
