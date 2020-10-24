package mnk;

import java.util.Arrays;

public class mnkBoard implements Board, Position{

    private final Cell[][] cells;
    private Cell turn;
    public static int nowc;
    public static int nowr;
    private int cellCounter = 0;

    public mnkBoard() {
        this.cells = new Cell[Peremen.n][Peremen.m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    private int Counter(int row, int column, int dx, int dy) {
        int counter = 0;
        for (int x = row, y = column; x < cells.length && x >= 0 && y < cells[0].length && y >= 0 && cells[x][y] == turn; x += dx, y += dy) {
            counter++;
        }
        return counter;
    }

    private boolean Checker(int row, int column, int dx, int dy) {
        return (Counter(row, column, dx, dy) + Counter(row, column, -dx, -dy) - 1) >= Peremen.k;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        cells[move.getRow()][move.getColumn()] = turn;
        cellCounter++;
        Result result = getResult(move);
        if (turn == Cell.X) {
            turn = Cell.O;
        } else if (turn == Cell.O) {
            turn = Cell.X;
        }
        return result;
    }

    private Result getResult(Move move) {
        if (Checker(move.getRow(), move.getColumn(), 0, 1) ||
                Checker(move.getRow(), move.getColumn(), 1, 0) ||
                Checker(move.getRow(), move.getColumn(), 1, 1) ||
                Checker(move.getRow(), move.getColumn(), 1, -1)) {
            return Result.WIN;
        }
        if (cellCounter == cells.length * cells[0].length) {
            return Result.DRAW;
        }
        return Result.UNKNOWN;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < cells.length
                && 0 <= move.getColumn() && move.getColumn() < cells[0].length
                && cells[move.getRow()][move.getColumn()] == Cell.E;
    }


    @Override
    public String toString() {
        int lenN = Integer.toString(cells.length).length(), lenM = Integer.toString(cells[0].length).length();
        StringBuilder sb = new StringBuilder(" ".repeat(lenN + 1));
        for (int i = 1; i <= cells[0].length; i++) {
            sb.append(String.format("%" + lenM + "d ", i));
        }
        for (int i = 0; i < cells.length; i++) {
            sb.append("\n");
            sb.append(String.format("%" + lenN + "d ", (i + 1)));
            for (int j = 0; j < cells[0].length; j++) {
                sb.append(String.format("%" + lenM + "s ", cells[i][j].toString()));
            }
        }
        return sb.toString();
    }
}
