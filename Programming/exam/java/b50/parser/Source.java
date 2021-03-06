package parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

class Source {
    private final InputStream is;
    private int ix = 0;
    private int markedIx = 0;
    Source(InputStream is) {
        this.is = new BufferedInputStream(is);
    }

    public void mark() {
        this.is.mark(Integer.MAX_VALUE);
        this.markedIx = ix;
    }

    public void reset() {
        try {
            this.is.reset();
            this.ix = markedIx;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public char next() {
        try {
            char c = (char) is.read();
            ix++;
            return c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasNext() {
        try {
            return this.is.available() != 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    RuntimeException error(String message) {
        String errMsg = String.format("[%d]: %s", this.ix, message);
        return new RuntimeException(errMsg);
    }
}
