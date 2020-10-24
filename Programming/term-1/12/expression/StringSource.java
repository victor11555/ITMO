package expression;

public class StringSource {
    private final String data;
    private int pos;
    private char ch;

    public StringSource(final String data) {
        this.data = data;
        nextChar();
    }

    public boolean hasNext() {
        return pos < data.length();
    }

    private char next() {
        return data.charAt(pos++);
    }

    public void nextChar() {
        ch = this.hasNext() ? this.next() : '\0';
    }

    public boolean test(char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    public void expect(final char c) {
        if (ch != c) {
            throw error("Expected '" + c + "', found '" + ch + "'");
        }
        nextChar();
    }

    public void expect(final String value) {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    public void skipwhitespace(){
        while(ch == ' ' || ch == '\t') {
            nextChar();
        }
    }

    public boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    public char getCurrentChar() { return this.ch; }

    public RuntimeException error(final String message) {
        return new RuntimeException(pos + ": " + message);
    }

}
