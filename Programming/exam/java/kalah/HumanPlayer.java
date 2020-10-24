import java.io.*;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;
    public int nowx;

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
            //можно сделать ход первого и ход второго
            out.println(cell + "'s move");
            out.println("Enter number of tee");
            boolean flag = true;
            while (flag) {
                Scanner in = new Scanner(System.in);
                flag = false;
                try {
                    out.println("Make move");
                    nowx = in.nextInt();
                    TicTacToeBoard.nowx = nowx;
                } catch (Exception e) {
                    flag = true;
                    System.err.println("Invalid input format " + e.getMessage());
                }
            }
            final Move move = new Move(nowx, cell);
            if (position.isValid(move)) {
                return move;
            }
            final int x = move.getRow();
            out.println("Move " + move + " is invalid");
        }
    }
}