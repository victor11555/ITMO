package mnk;
import java.util.Scanner;

public class Peremen {

        public static int m, n, k, laps, cnt;

        public void StartScan(){
        Scanner sc = new Scanner(System.in);
        while (m <= 0) {
            System.out.println("Сколько строк?");
            if (sc.hasNextInt()) {
                m = sc.nextInt();
            } else {
                System.out.println("Некорректный ввод");
                m = 0;
                sc = new Scanner(System.in);
            }
        }


        while (n <= 0) {
            System.out.println("Сколько столбцов?");
            if (sc.hasNextInt()) {
                n = sc.nextInt();
            } else {
                System.out.println("Некорректный ввод");
                n = 0;
                sc = new Scanner(System.in);
            }
        }


        while (k <= 0) {
            System.out.println("Сколько в ряд?");
            if (sc.hasNextInt()) {
                k = sc.nextInt();
            } else {
                System.out.println("Некорректный ввод");
                k = 0;
                sc = new Scanner(System.in);
            }

        }


        while (laps <= 0) {
            System.out.println("Сколько кругов?");
            if (sc.hasNextInt()) {
                laps = sc.nextInt();
            } else {
                System.out.println("Некорректный ввод");
                laps = 0;
                sc = new Scanner(System.in);
            }
        }


        while (cnt <= 0) {
            System.out.println("Сколько игроков?");
            if (sc.hasNextInt()) {
                cnt = sc.nextInt();
            } else {
                System.out.println("Некорректный ввод");
                cnt = 0;
                sc = new Scanner(System.in);
            }
        }
    }
}