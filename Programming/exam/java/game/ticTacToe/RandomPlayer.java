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
            int c = random.nextInt(Race.size.m);
            final Move move = new Move(c, cell);
            if (position.isValid(move)) {
                TicTacToeBoard.nowc = c;
                //System.out.println(position);
                System.out.println(cell + "'s move");
                return move;
            }
        }
    }
}