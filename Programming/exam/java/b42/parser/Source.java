package parser;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

class Source {
    private final BufferedReader is;
    private int ix = 0;
    private int markedIx = 0;
    Source(InputStream is) {
        this.is = new BufferedReader(new InputStreamReader( new BufferedInputStream(is)));
    }

    public void mark() {
        try {
            this.is.mark(Integer.MAX_VALUE);
            this.markedIx = ix;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
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
            return this.is.ready();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    RuntimeException error(String message) {
        String errMsg = String.format("[%d]: %s", this.ix, message);
        return new RuntimeException(errMsg);
    }
}
