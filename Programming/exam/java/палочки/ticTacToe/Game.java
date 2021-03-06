package ticTacToe;

public class Game {
    private final boolean log;
    private final Player player1, player2;

    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) {
        while (true) {
            int result1 = move(board, player1, 1);
            if (result1 != -1) {
                return result1;
            }
            while(Race.size.flags) {
                result1 = move(board, player1, 1);
                if (result1 != -1) {
                    return result1;
                }
            }
            
            int result2 = move(board, player2, 2);
            if (result2 != -1) {
                return result2;
            }
            while(Race.size.flags) {
                result2 = move(board, player2, 2);
                if (result2 != -1) {
                    return result2;
                }
            }
        }
    }

    private int move(final Board board, final Player player, final int no) {
        final Move move = player.move(board.getPosition(), board.getCell());
        final Result result = board.makeMove(move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            return no;
        } else if (result == Result.LOSE) {
            return 3 - no;
        } else if (result == Result.DRAW) {
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
            System.out.println(message);
    }
}