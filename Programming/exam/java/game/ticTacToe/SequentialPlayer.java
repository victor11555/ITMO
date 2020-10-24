package ticTacToe;

public class SequentialPlayer implements Player {
    @Override
    public Move move(final Position position, final Cell cell) {
//        Board board = (Board) position;
//        board.makeMove()
        for (int c = 0; c < Race.size.m; c++) {
            final Move move = new Move(c, cell);
            if (position.isValid(move)) {
                TicTacToeBoard.nowc = c;
                //System.out.println(position);
                System.out.println(cell + "'s move");
                return move;
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
