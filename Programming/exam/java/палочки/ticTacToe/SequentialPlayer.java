package ticTacToe;

public class SequentialPlayer implements Player {
    @Override
    public Move move(final Position position, final Cell cell) {
         for (int r = 0; r < Race.size.n; r++) {
            for (int c = 0; c < Race.size.m; c++) {
                final Move move = new Move(r, c, cell);
                if (position.isValid(move)) {
                    TicTacToeBoard.nowr = r;
                    TicTacToeBoard.nowc = c;
                    System.out.println(cell + "'s move");
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
