public class SequentialPlayer implements Player {
    @Override
    public Move move(final Position position, final Cell cell) {
//        Board board = (Board) position;
//        board.makeMove()
        for (int x = 1; x <= 6; x++) {
            final Move move = new Move(x, cell);
            if (position.isValid(move)) {
                TicTacToeBoard.nowx = x;
                //System.out.println(position);
                //ход 1 или 2
                System.out.println(cell + "'s move");
                return move;
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
