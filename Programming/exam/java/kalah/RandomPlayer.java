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
            int x = random.nextInt(7);
            final Move move = new Move(x, cell);
            if (position.isValid(move)) {
                TicTacToeBoard.nowx = x;
                //System.out.println(position);
                //ход 1 или 2
                System.out.println(cell + "'s move");
                return move;
            }
        }
    }
}