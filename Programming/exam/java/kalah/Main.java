import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int tyr = 1;
        if (tyr == 1) {
            StringBuilder result = new StringBuilder();
            boolean flag = true;
            int c = 0;
            while (flag) {
                Scanner sc = new Scanner(System.in);
                try {
                    flag = false;
                    System.out.println("Write number of circles c:");
                    c = sc.nextInt();
                    if (c <= 0) {
                        System.err.println("Invalid value c");
                        flag = true;
                    }
                } catch (Exception e) {
                    flag = true;
                    System.err.println("Invalid input format");
                }
            }
            result = (new Race(false)).tour(c);
            System.out.println("Game result: " + "\n" + result);
        } else {
            final Game game = new Game(false, new RandomPlayer(), new RandomPlayer());
            int result;
            // do {
            result = game.play(new TicTacToeBoard());
            System.out.println("Game result: " + result);
            // } while (result != 0);
        }
    }
}
