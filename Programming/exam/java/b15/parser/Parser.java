package parser;

import java.io.InputStream;

public class Parser {

    private final Source source;
    private char currentChar = '\0';

    protected Parser(InputStream is) {
        this.source = new Source(is);
    }

    protected final char getCurrentChar() {
        if (this.currentChar == '\0') {
            if (this.hasNext()) {
                this.nextChar();
            } else {
                throw new RuntimeException("Поток символов истощен");
            }
        }
        return this.currentChar;
    }

    protected final boolean hasNext() {
        return this.source.hasNext();
    }

    protected final RuntimeException error(String msg) {
        return this.source.error(msg);
    }

    protected final char nextChar() {
        this.currentChar = this.source.hasNext()
                ? this.source.next()
                : '\0';
        return this.currentChar;
    }

    protected final boolean test(char expected) {
        if (this.getCurrentChar() == expected) {
            this.nextChar();
            return true;
        }
        return false;
    }

    protected final boolean testAndReset(String str) {
        this.source.mark();
        char tmp = this.currentChar;
        boolean result = false;
        for (char c : str.toCharArray()) {
            result = this.test(c);
            if (!result) break;
        }
        this.source.reset();
        this.currentChar = tmp;
        return result;
    }

    protected final void expect(char c) {
        if (this.currentChar == '\0' && !this.hasNext()) {
            String errMsg = String.format("Ожидался '%s', но получили конец потока"
                    , c
            );
            throw this.source.error(errMsg);
        }
        if (this.getCurrentChar() != c) {
            String errMsg = String.format("Ожидался '%s', но получили '%s'"
                    , c
                    , this.getCurrentChar()
            );
            throw this.source.error(errMsg);
        }
        nextChar();
    }

    protected final void expect(String value) {
        for (char c : value.toCharArray()) {
            this.expect(c);
        }
    }

    protected final void skipBlank() {
        while (this.hasNext()
            && (this.test(' ')
            ||  this.test('\t')
            ||  this.test('\n')
            ||  this.test('\r')
               )
        ) {}
    }
}
