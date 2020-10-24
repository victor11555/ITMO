package ticTacToe;

import java.util.Scanner;
import java.io.*;

public class Race {
    static Size size;
    private final boolean log;
    public Race(final boolean log) {
        this.log = log;
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
                    temp = game.play(new TicTacToeBoard());
                    if (temp == 0) {
                        pl[i]++;
                        pl[j]++;
                        System.out.println("DRAW" + "\n");
                        System.out.println("Each players has " + Race.size.o + " points");
                    } else if (temp == 1) {
                        pl[i] += 3;
                        System.out.println("Player " + (i + 1) + " WON" + "\n");
                        if (Race.size.o > Race.size.x) {
                            System.out.println("Player " + (i + 1) + " has " + Race.size.o + " points, Player " + (j+1) + " has "+ Race.size.x + " points");
                        } else {
                            System.out.println("Player " + (i + 1) + " has " + Race.size.x + " points, Player " + (j+1) + " has "+ Race.size.o + " points");
                        }
                        System.out.println();
                    } else {
                        pl[j] += 3;
                        System.out.println("Player " + (j + 1) + " WON" + "\n");
                        if (Race.size.o > Race.size.x) {
                            System.out.println("Player " + (j + 1) + " has " + Race.size.o + " points, Player " + (i+1) + " has "+ Race.size.x + " points");
                        } else {
                            System.out.println("Player " + (j + 1) + " has " + Race.size.x + " points, Player " + (i+1) + " has "+ Race.size.o + " points");
                        }
                        System.out.println();
                    }
                    Race.size.o = 0;
                    Race.size.x = 0;
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