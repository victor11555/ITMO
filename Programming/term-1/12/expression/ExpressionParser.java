package expression;

public class ExpressionParser implements Parser  {

    @Override
    public TripleExpression parse(String expression) {
        StringSource source = new StringSource(expression);
        TripleExpression expr = this.parseExpression(source);
        if(source.hasNext()){
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

    private TripleExpression parseExpression(StringSource source) {
        source.skipwhitespace();
        TripleExpression term = this.parseTerm(source);
        source.skipwhitespace();
        while (source.getCurrentChar() == '+' || source.getCurrentChar() == '-') {
            char op = source.getCurrentChar();
            source.nextChar();
            source.skipwhitespace();
            TripleExpression term2 = this.parseTerm(source);
            if(op == '+') {
                term = new Add(term, term2);
            }
            else {
                term = new Subtract(term, term2);
            }
        }
        return term;
    }
    
    private TripleExpression parseTerm(StringSource source) {
        source.skipwhitespace();
        TripleExpression factor = this.parseFactor(source);
        source.skipwhitespace();
        while (source.getCurrentChar() == '*' || source.getCurrentChar() == '/') {
            char op = source.getCurrentChar();
            source.nextChar();
            source.skipwhitespace();
            TripleExpression factor2 = this.parseFactor(source);
            if(op == '*') {
                factor = new Multiply(factor, factor2);
            }
            else {
                factor = new Divide(factor, factor2);
            }
        }
        return factor;
    }

    private TripleExpression parseFactor(StringSource source) {
        source.skipwhitespace();
        TripleExpression factor;
        boolean isNegated = false;
        while (source.test('-')){
            source.skipwhitespace();
            isNegated = ! isNegated;
        }
        if(source.test('(')){
            factor =  parseExpression(source);
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
            factor = new Negate(factor);
        }
        return factor;
    }

    private TripleExpression parseVariable(StringSource source) {
        TripleExpression var = new Variable(source.getCurrentChar() + "");
        source.nextChar();
        source.skipwhitespace();
        return var;
    }

    private TripleExpression parseConst(StringSource source, boolean isNegated) {
        StringBuilder sb = new StringBuilder(isNegated ? "-" : "");
        while (source.between('0','9')){
            sb.append(source.getCurrentChar());
            source.nextChar();
        }
        source.skipwhitespace();
        return new Const(Integer.parseInt(sb.toString()));
    }


}
