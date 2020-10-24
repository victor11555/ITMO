import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board, Position {
    //private static final Map<Cell, Character> SYMBOLS = Map.of(Cell.X, 'X', Cell.O, 'O');

    private final int[] tees;
    private Cell turn;
    private Cell turnBefore;
    public static int nowx;

    public TicTacToeBoard() {
        this.tees = new int[13];
        for (int i = 0; i < 6; i++) {
            tees[i] = Race.size.kol;
            tees[i+7] = Race.size.kol;
        }
        tees[6] = 0;
        turn = Cell.X;
        turnBefore = turn;
    }


    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    /*@Override
    public Cell getCell(final int r, final int c) {
        return tees[r][c];
    }*/

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        if (turn == Cell.X) {
            for (int i = 0; i < 6; i++) {
                tees[i] = Race.size.oneTwo[0][i];
                tees[i+7] = Race.size.oneTwo[1][i];
            }
            tees[6] = Race.size.kalah[0];
        } else {
            tees[6] = Race.size.kalah[1];
            for (int i = 0; i < 6; i++) {
                tees[i] = Race.size.oneTwo[1][i];
                tees[i+7] = Race.size.oneTwo[0][i];
            }
        }
        int n = tees[nowx-1];
        tees[nowx-1]=0;
        for (int i = nowx; i < nowx+n; i++) {
            tees[i%13]++;
        }
        if ((nowx+n-1)%13 < 6 && tees[(nowx+n-1)%13] == 1 && tees[12-((nowx+n-1)%13)] != 0) {
            tees[6] += 1+tees[12-((nowx+n-1)%13)];
            tees[(nowx+n-1)%13] = 0;
            tees[12-((nowx+n-1)%13)] = 0;
        }

        Race.size.flags = false;
        if ((nowx+n-1)%13 == 6) {
            Race.size.flags = true;
        }


        if (Race.size.kalah[0] == Race.size.kalah[1] && Race.size.kalah[0] == Race.size.kol*6) {
            return Result.DRAW;
        }

        int empty1 = 0, empty2 = 0;
        if (turn == Cell.X) {
            for (int i = 0; i < 6; i++) {
                Race.size.oneTwo[0][i] = tees[i];
                Race.size.oneTwo[1][i] = tees[i+7];
                if (tees[i] == 0) {
                    empty1++;
                }
                if (tees[i+7] == 0) {
                    empty2++;
                }
            }
            Race.size.kalah[0] = tees[6];
            //System.out.println(tees[6]+" >>>>>> " + Race.size.kalah[0]);
        } else {
            for (int i = 0; i < 6; i++) {
                Race.size.oneTwo[1][i] = tees[i];
                Race.size.oneTwo[0][i] = tees[i+7];
                if (tees[i] == 0) {
                    empty2++;
                }
                if (tees[i+7] == 0) {
                    empty1++;
                }
            }
            Race.size.kalah[1] = tees[6];
            //System.out.println(tees[6]+" >> " + Race.size.kalah[0]);
        }

        if (empty1 == 6) {
            Race.size.kalah[1] = Race.size.kol*12-Race.size.kalah[0];
            for (int i = 0; i < 6; i++) {
                Race.size.oneTwo[1][i] = 0;
            }
            return Result.WIN;
        }
        if (empty2 == 6) {
            Race.size.kalah[0] = Race.size.kol*12-Race.size.kalah[1];
            for (int i = 0; i < 6; i++) {
                Race.size.oneTwo[0][i] = 0;
            }
            return Result.WIN;
        }
        if (Race.size.kalah[0] > Race.size.kol*6 || Race.size.kalah[1] > Race.size.kol*6 ) {
            return Result.WIN;
        }
        turnBefore = turn;
        if (!Race.size.flags) {
            turn = turn == Cell.X ? Cell.O : Cell.X;
        }
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        //System.out.println(".... " + (0 < move.getRow()) + " " + (move.getRow() < 7) + " " +  (turn == getCell()) + " ");
        return 0 < move.getRow() && move.getRow() < 7
                && turn == getCell() && (turn == Cell.X && Race.size.oneTwo[0][move.getRow()-1] != 0 || turn == Cell.O && Race.size.oneTwo[1][move.getRow()-1] != 0);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("2nd");
        for (int re = 6; re > 0; re--) {
            sb.append(" ");
            sb.append(re);
        }
        sb.append("\n");
        /*for (int r = 12; r > 6; r--) {
            sb.append(" ");
            sb.append(tees[r]);
        }
        sb.append("\n");
        if (turnBefore == Cell.X) {
            sb.append(Race.size.kalah[1] + "           "+ Race.size.kalah[0]+ "\n");
        } else {
            sb.append(Race.size.kalah[0] + "           "+ Race.size.kalah[1]+ "\n");
        }
        for (int r = 0; r < 6; r++) {
            sb.append(" ");
            sb.append(tees[r]);
        }*/
        sb.append("   ");
        for (int r = 5; r >= 0; r--) {
            sb.append(" ");
            sb.append(Race.size.oneTwo[1][r]);
        }
        sb.append("\n");
        sb.append(" " + Race.size.kalah[1] + "               "+ Race.size.kalah[0]+ "\n");
        sb.append("   ");
        for (int r = 0; r < 6; r++) {
            sb.append(" ");
            sb.append(Race.size.oneTwo[0][r]);
        }
        sb.append("\n");
        sb.append("1st");
        for (int re = 1; re < 7; re++) {
            sb.append(" ");
            sb.append(re);
        }
        return sb.toString();
    }
}
