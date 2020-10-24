package expression.generic.parser;

import expression.generic.Parser;
import expression.generic.TripleExpression;

public class ExpressionParser<R> implements Parser<R> {

    ExpressionBuilder<R> expressionBuilder;
    public ExpressionParser(ExpressionBuilder<R> expressionBuilder){
        this.expressionBuilder = expressionBuilder;
    }

    @Override
    public TripleExpression<R> parse(String expression) throws ParseException {
        StringSource source = new StringSource(expression);
        TripleExpression<R> expr = this.parseExpression(source);
        if(source.hasNext() || source.getCurrentChar() == ')' || source.getCurrentChar() == '@'){
            throw source.error("Expected end of stream");
        }
        return expr;
    }
    /*
    < expression > ::= < term > + < expression >   |    < term > - < expression > | < term >

    < term > ::=    < factor > * < term >     |    < factor > / < term >   |   < factor >

    < factor > ::=   -[  (< expression >)     |        < const >     |    < variable > ]
     - может не/быть в начале всего того что в [ ... | ... | ... ]
     */

    private TripleExpression<R> parseExpression(StringSource source) throws ParseException{
        source.skipwhitespace();
        TripleExpression<R> term = this.parseTerm(source);
        source.skipwhitespace();
        while (source.getCurrentChar() == '+' || source.getCurrentChar() == '-') {
            char op = source.getCurrentChar();
            source.nextChar();
            source.skipwhitespace();
            TripleExpression<R> term2 = this.parseTerm(source);
            if(op == '+') {
                term = this.expressionBuilder.add(term, term2);
            }
            else {
                term = this.expressionBuilder.subtract(term, term2);
            }
        }
        return term;
    }
    
    private TripleExpression<R> parseTerm(StringSource source) throws ParseException {
        source.skipwhitespace();
        TripleExpression<R> factor = this.parseFactor(source);
        source.skipwhitespace();
        while (source.getCurrentChar() == '*' || source.getCurrentChar() == '/') {
            char op = source.getCurrentChar();
            source.nextChar();
            source.skipwhitespace();
            TripleExpression<R> factor2 = this.parseFactor(source);
            if(op == '*') {
                factor = this.expressionBuilder.multiply(factor, factor2);
            }
            else {
                factor = this.expressionBuilder.divide(factor, factor2);
            }
        }
        return factor;
    }

    private TripleExpression<R> parseFactor(StringSource source) throws ParseException{
        source.skipwhitespace();
        TripleExpression<R> factor;
        boolean isNegated = false;
        while (source.test('-')){
            source.skipwhitespace();
            isNegated = ! isNegated;
        }
        if(source.test('(')){
            factor = parseExpression(source);
            source.expect(')');
            source.skipwhitespace();
        } 
        else if(source.between('0', '9')){
            factor = parseConst(source, isNegated);
            isNegated = false;
        }
        else if(source.between('x', 'z')){
            factor = parseVariable(source);
        }
        else {
            throw source.error("Unknown factor");
        }

        if(isNegated){
            factor = this.expressionBuilder.negate(factor);
        }
        return factor;
    }

    private TripleExpression<R> parseVariable(StringSource source) {
        TripleExpression<R> var = this.expressionBuilder.variable(source.getCurrentChar());
        source.nextChar();
        source.skipwhitespace();
        return var;
    }

    private TripleExpression<R> parseConst(StringSource source, boolean isNegated) throws ParseException{
        StringBuilder sb = new StringBuilder(isNegated ? "-" : "");
        while (source.between('0','9')){
            sb.append(source.getCurrentChar());
            source.nextChar();
        }
        source.skipwhitespace();
        return this.expressionBuilder.constant(sb.toString());
    }


}
