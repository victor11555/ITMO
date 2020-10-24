public final class Move {
    private final int row;
    private final Cell value;

    public Move(final int row, final Cell value) {
        this.row = row;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public Cell getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "row=" + row + ", value=" + value;
    }
}