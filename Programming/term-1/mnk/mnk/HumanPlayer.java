package mnk;

import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            int tmpr;
            int tmpc;
            if (in.hasNextInt()) {
                tmpr = in.nextInt();
            } else {
                tmpr = -1;
                in = new Scanner(System.in);
            }
            if (in.hasNextInt()) {
                tmpc = in.nextInt();
            } else {
                tmpc = -1;
                in = new Scanner(System.in);
            }


            mnkBoard.nowc = tmpc;
            mnkBoard.nowr = tmpr;
            final Move move = new Move(tmpr, tmpc, cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getColumn();
            out.println("Move " + move + " is invalid");
        }
    }
}
