import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class E {

    public static class Apple {
        private int balance;
        private int down;
        private int index;

        public Apple(int down, int up, int index) {
            this.balance = up - down;
            this.down = down;
            this.index = index;
        }

        public static Comparator<Apple> Ascending = new Comparator<Apple>() {
            public int compare(Apple s1, Apple s2) {
                return s1.down - s2.down;
            }
        };

        public static Comparator<Apple> Descending = new Comparator<Apple>() {
            public int compare(Apple s1, Apple s2) {
                return (s2.balance+s2.down) - (s1.balance+s1.down);
            }
        };

    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("apples.in"));
        int n = sc.nextInt();
        int sum = sc.nextInt();
        ArrayList<Apple> positive = new ArrayList<>();
        ArrayList<Apple> negative = new ArrayList<>();
        int down, up;
        for (int i = 0; i < n; i++) {
            down = sc.nextInt();
            up = sc.nextInt();
            if(up >= down) {
                positive.add(new Apple(down, up, i));
                sum += up-down;
            }else {
                negative.add(new Apple(down, up, i));
            }
        }
        Collections.sort(positive, Apple.Ascending);
        Collections.sort(negative, Apple.Descending);
        try(FileWriter writer = new FileWriter("apples.out")) {
            boolean flag = true;
            for (int i = 0; i < negative.size(); i++) {
                if (sum - negative.get(i).down > 0) {
                    sum += negative.get(i).balance;
                } else {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                for (int i = 0; i < positive.size(); i++) {
                    writer.write(Integer.toString(positive.get(i).index + 1) + " ");
                }
                for (int i = 0; i < negative.size(); i++) {
                    writer.write(Integer.toString(negative.get(i).index + 1) + " ");
                }
            }
            else {
                writer.write("-1" + "\n");
            }
        }
    }
}