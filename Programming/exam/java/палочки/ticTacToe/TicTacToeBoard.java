package ticTacToe;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(Cell.X, 'X', Cell.O, 'O', Cell.E, '.', Cell.F, '+');

    private final Cell[][] cells;
    private Cell turn;
    public static int nowc;
    public static int nowr;

    public TicTacToeBoard() {
        this.cells = new Cell[Race.size.n][Race.size.m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    private boolean check(int r1, int r2, int r3, int const1, int c, int c2) {
        return r1 >= 0 && r2 >= 0 && r3 >= 0 && c >= 0 && c2 >= 0
            && r1 < Race.size.n && r2 < Race.size.n && r3 < Race.size.n && c < Race.size.m && c2 < Race.size.m
            && nowr % 2 == const1 && cells[r1][c] == Cell.F
            && cells[r2][c] == Cell.F  && cells[r3][c2] == Cell.F
        ;
        
    }


    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        cells[move.getRow()][move.getColumn()] = Cell.F;
        Race.size.flags = false;
        
        int empty = 0;
        int flag = 0;
        if (check(nowr+1, nowr+2, nowr+1, 0, nowc, nowc+1)) {
            flag++;
        }
        if (check(nowr-1, nowr-2, nowr-1, 0, nowc, nowc+1)) {
            flag++;
        }
        if (check(nowr-1, nowr+1, nowr, 1, nowc, nowc+1)) {
            flag++;
        }
        if (check(nowr, nowr-1, nowr+1, 1, nowc-1, nowc-1)) {
            flag++;
        }

        if (flag != 0) {
            Race.size.flags = true;
            if (turn == Cell.X) {
                Race.size.x += flag;
            } else {
                Race.size.o += flag;
            }
        }

        for (int u = 0; u < Race.size.n; u++) {
            for (int v = 0; v < Race.size.m; v++) {
                if (cells[u][v] == Cell.E) {
                    empty++;
                }
            }
        }
        if (empty == (Race.size.n+1)/2) {
            if (Race.size.x > Race.size.o && move.getValue() == Cell.X || Race.size.o > Race.size.x && move.getValue() == Cell.O) {
                return Result.WIN;
            } else if (Race.size.x == Race.size.o) {
                return Result.DRAW;
            } else {
                return Result.LOSE;
            }
        }
        if (flag == 0) {
            turn = turn == Cell.X ? Cell.O : Cell.X;
        }
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < Race.size.n && 0 <= move.getColumn()
                && move.getColumn() < Race.size.m && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell() && (move.getRow() % 2 == 0 && move.getColumn() != Race.size.m-1 || move.getRow() % 2 == 1);
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("  ");
        for (int re = 0; re < Race.size.m; re++) {
            sb.append(re);
        }
        for (int r = 0; r < Race.size.n; r++) {
            sb.append("\n");
            sb.append(r);
            sb.append(" ");
            if (r % 2 == 0) {
                sb.append(" ");
            }
            for (int c = 0; c < Race.size.m; c++) {
                if (SYMBOLS.get(cells[r][c]) == '+') {
                    if (r % 2 == 0) {
                        sb.append("- ");
                    } else {
                        sb.append("| ");
                    }
                } else if (!(r % 2 == 0 && c == Race.size.m-1)){
                    sb.append(SYMBOLS.get(cells[r][c]));
                }
            }
        }
        sb.append("\n" + Race.size.o + " - O" + "\n");
        sb.append(Race.size.x + " - X");
        return sb.toString();
    }
}
