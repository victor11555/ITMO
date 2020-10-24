import java.util.Scanner;
import java.io.*;

public class Race {
    static Size size;
    private final boolean log;

    // private final Player player1, player2;
    public Race(final boolean log) {
        this.log = log;
        // this.player1 = player1;
        // this.player2 = player2;
    }

    public StringBuilder tour(int c) {
        size = new Size();
        StringBuilder res = new StringBuilder();
        int temp = 0, ko = 0;
        boolean flag = true;
        while (flag) {
            Scanner sc = new Scanner(System.in);
            flag = false;
            try {
                System.out.println("Write number of players");
                ko = sc.nextInt();
                if (ko < 2) {
                    System.out.println("Wrong value");
                    flag = true;
                }
            } catch (Exception e) {
                flag = true;
                System.err.println("Invalid number of players");
            }
        }
        int[] pl = new int[ko];
        Player[] id = new Player[ko];
        flag = true;
        int me;
        while (flag) {
            Scanner sc = new Scanner(System.in);
            flag = false;
            try {
                System.out.println("Choose " + ko + " players(1-random, 2-sequintal, 3-human):");
                for (int i = 0; i < ko; i++) {
                    me = sc.nextInt();
                    if (me == 1) {
                        id[i] = new RandomPlayer();
                    } else if (me == 2) {
                        id[i] = new SequentialPlayer();
                    } else if (me == 3) {
                        id[i] = new HumanPlayer();
                    } else {
                        System.out.println("Invalid type (!(1, 2, 3))");
                        flag = true;
                    }
                }
            } catch (Exception e) {
                flag = true;
                System.err.println("Invalid type players");
            }
        }
        for (int i = 0; i < ko - 1; i++) {
            for (int j = i + 1; j < ko; j++) {
                Game game = new Game(this.log, id[i], id[j]);
                for (int s = 0; s < c; s++) {
                    for (int p = 0; p < 6; p++) {
                        Race.size.oneTwo[0][p] = Race.size.kol;
                        Race.size.oneTwo[1][p] = Race.size.kol;
                        //System.out.println(Race.size.oneTwo[0][i]+" " + Race.size.oneTwo[1][i]);
                    }
                    temp = game.play(new TicTacToeBoard());
                    if (temp == 0) {
                        pl[i]++;
                        pl[j]++;
                        System.out.println("DRAW" + "\n");
                        System.out.println("Each players has " + Race.size.kalah[1] + " points");
                    } else if (temp == 1) {
                        if (Race.size.kalah[1] > Race.size.kalah[0]) {
                            pl[j] += 3;
                            System.out.println("Player " + (j + 1) + " WON" + "\n");
                            System.out.println("Player " + (j + 1) + " has " + Race.size.kalah[1] + " points, Player " + (i+1) + " has "+ Race.size.kalah[0] + " points");
                        } else {
                            pl[i] += 3;
                            System.out.println("Player " + (i + 1) + " WON" + "\n");
                            System.out.println("Player " + (i + 1) + " has " + Race.size.kalah[0] + " points, Player " + (j+1) + " has "+ Race.size.kalah[1] + " points");
                        }
                        System.out.println();
                    } else {
                        if (Race.size.kalah[1] > Race.size.kalah[0]) {
                            pl[j] += 3;
                            System.out.println("Player " + (j + 1) + " WON" + "\n");
                            System.out.println("Player " + (j + 1) + " has " + Race.size.kalah[1] + " points, Player " + (i+1) + " has "+ Race.size.kalah[0] + " points");
                        } else {
                            pl[i] += 3;
                            System.out.println("Player " + (i + 1) + " WON" + "\n");
                            System.out.println("Player " + (i + 1) + " has " + Race.size.kalah[0] + " points, Player " + (j+1) + " has "+ Race.size.kalah[1] + " points");
                        }
                        System.out.println();
                    }
                    Race.size.kalah[1] = 0;
                    Race.size.kalah[0] = 0;
                }
            }
        }
        res.append("\n");
        for (int i = 0; i < ko; i++) {
            res.append("Player " + (i + 1) + "  Total: " + pl[i] + "\n");
        }
        return res;
    }
}