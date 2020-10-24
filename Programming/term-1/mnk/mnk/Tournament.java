package mnk;

import java.util.ArrayList;

public class Tournament {

    private final int[] points;
    private int lap;

    final ArrayList<Player> players = new ArrayList<>();


    public Tournament() {
        this.lap = 1;
        this.points = new int[Peremen.cnt];
        for (int i = 0; i < Peremen.cnt; i++) {
            this.points[i] = 0;
        }
        for (int i = 0; i < Peremen.cnt; i++){
            this.players.add(new HumanPlayer());
        }
    }

    public void runTournament() {

        while (lap <= Peremen.laps) {
            for (int i = 0; i < Peremen.cnt - 1; i++) {
                for (int j = i + 1; j < Peremen.cnt; j++) {
                    System.out.println("Lap: " + lap + ". " + "Играют: игрок " + i + " и игрок " + j + "\n");
                    final Game game = new Game(true, players.get(i), players.get(j));
                    int result = game.play(new mnkBoard());
                    switch (result) {
                        case 1: {
                            points[i] += 3;
                            break;
                        }
                        case 2: {
                            points[j] += 3;
                            break;
                        }
                        case 0: {
                            points[i]++;
                            points[j]++;
                            break;
                        }
                    }
                    getTable();
                }
            }
            this.lap++;
        }
        getWinner();
    }

    private void getTable() {
        System.out.println("Круг: " + lap + "\n" + "Таблица баллов:" + "\n" + "Игроки:  ");
        for (int i = 0; i < Peremen.cnt; i++)
            System.out.println(i + " ");
        System.out.println("\n" + "Баллы: ");
        for (int i = 0; i < Peremen.cnt; i++)
            System.out.println(points[i] + " ");
        System.out.println("\n");
    }

    private void getWinner() {
        int max = -1;
        int id = -1;
        for (int i = 0; i < Peremen.cnt; i++)
            if (points[i] > max) {
                id = i;
                max = points[i];
            }
        for (int i = 0; i < Peremen.cnt; i++)
            if (points[i] == max && i != id) {
                System.out.println("No absolute winner!" + "\n");
                return;
            }
        System.out.println("Absolute winner is player " + id + "\n");
    }


}