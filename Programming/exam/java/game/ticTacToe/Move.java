package ticTacToe;

public final class Move {
    private final int column;
    private final Cell value;

    public Move(final int column, final Cell value) {
        this.column = column;
        this.value = value;
    }

    public int getColumn() {
        return column;
    }

    public Cell getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "column=" + column + ", value=" + value;
    }
}