package mnk;

public class Main{

    public static void main(String[] args) {
        Peremen peremen = new Peremen();
        peremen.StartScan();
        Tournament tournament = new Tournament();
        tournament.runTournament();
    }
}
