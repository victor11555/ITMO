package mnk;

import java.util.Random;


public class RandomPlayer  implements Player  {

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
            int r = random.nextInt(Peremen.n);
            int c = random.nextInt(Peremen.m);
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                mnkBoard.nowr = r;
                mnkBoard.nowc = c;
                return move;
            }
        }
    }
}
