package ticTacToe;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            int r = random.nextInt(Race.size.n);
            int c = random.nextInt(Race.size.m);
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                TicTacToeBoard.nowr = r;
                TicTacToeBoard.nowc = c;
                System.out.println(cell + "'s move");
                return move;
            }
        }
    }
}