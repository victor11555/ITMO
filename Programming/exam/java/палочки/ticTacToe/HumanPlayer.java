package ticTacToe;

import java.io.*;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;
    public int nowr;
    public int nowc;

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
            out.println(cell + "'s move");
            out.println("Enter row and column");
            boolean flag = true;
            while (flag) {
                Scanner in = new Scanner(System.in);
                flag = false;
                try {
                    out.println("Make move");
                    nowr = in.nextInt();
                    TicTacToeBoard.nowr = nowr;
                    nowc = in.nextInt();
                    TicTacToeBoard.nowc = nowc;
                } catch (Exception e) {
                    flag = true;
                    System.err.println("Invalid input format " + e.getMessage());
                }
            }
            final Move move = new Move(nowr, nowc, cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getColumn();
            out.println("Move " + move + " is invalid");
        }
    }
}