package ticTacToe;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(Cell.X, 'X', Cell.O, 'O', Cell.E, '.');

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

    private int check(int const1, int const2, int nowco, int m) {
        int u = 1;
        int inDiag1 = 1;
        int right = 1;
        int left = 1;
        while ((right == 1 || left == 1) && u < Race.size.k
                && ((nowco + u < m
                        && ((const1 + const2) % 2 == 1 || nowr + u * const1 < Race.size.n && nowr + const1 * u >= 0))
                        || (nowco - u >= 0 && ((const1 + const2) % 2 == 1
                                || nowr - const1 * u >= 0 && nowr - const1 * u < Race.size.n)))) {
            if (right == 1 && nowco + u < m
                    && ((const1 + const2) % 2 == 1 || nowr + u * const1 < Race.size.n && nowr + const1 * u >= 0)) {
                if (cells[nowr + const1 * u][nowc + const2 * u] == turn) {
                    inDiag1++;
                } else {
                    right = 0;
                }
            }
            if (left == 1 && nowco - u >= 0
                    && ((const1 + const2) % 2 == 1 || nowr - const1 * u >= 0 && nowr - const1 * u < Race.size.n)) {
                if (cells[nowr - const1 * u][nowc - const2 * u] == turn) {
                    inDiag1++;
                } else {
                    left = 0;
                }
            }
            u++;
        }
        if (inDiag1 == Race.size.k) {
            return 1;
        } else {
            return -6;
        }
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
        for (int u = Race.size.n-1; u >= 0; u--) {
            if (cells[u][nowc] == Cell.E) {
                nowr = u;
                cells[nowr][nowc] = move.getValue();
                break;
            }
        }
        
        int empty = 0;

        int e = -2;
        e = check(0, 1, nowc, Race.size.m);
        if (e != -6) {
            return Result.WIN;
        }
        e = check(1, 0, nowr, Race.size.n);
        if (e != -6) {
            return Result.WIN;
        }
        e = check(1, 1, nowc, Race.size.m);
        if (e != -6) {
            return Result.WIN;
        }
        e = check(-1, 1, nowc, Race.size.m);
        if (e != -6) {
            return Result.WIN;
        }

        for (int u = 0; u < Race.size.n; u++) {
            for (int v = 0; v < Race.size.m; v++) {
                if (cells[u][v] == Cell.E) {
                    empty++;
                }
            }
        }
        if (empty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getColumn()
                && move.getColumn() < Race.size.m && cells[0][move.getColumn()] == Cell.E
                && turn == getCell();
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
            for (int c = 0; c < Race.size.m; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
