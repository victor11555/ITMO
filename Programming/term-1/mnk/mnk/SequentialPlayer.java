package mnk;


public class SequentialPlayer implements Player {


    @Override
    public Move move(final Position position, final Cell cell) {
        for (int r = 0; r < Peremen.n; r++) {
            for (int c = 0; c < Peremen.m; c++) {
                final Move move = new Move(r, c, cell);
                if (position.isValid(move)) {
                    mnkBoard.nowr = r;
                    mnkBoard.nowc = c;
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
