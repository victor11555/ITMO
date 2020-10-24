import java.util.*;
import java.util.Map;

public class Chess {
    public static boolean finish;
    public static int co, r;
    public static int[][] white;
    public static int[] blackKing;
    public static Scanner sc;
    public static TreeMap<Character, Integer> column = new TreeMap<>();

    public static boolean isValid(int c, int r, int color) {
        return c < 8 && r < 8 && c > -1 && r > -1 && (color == 2 || color == 1 && (white[0][0] != c && white[0][1] != r || white[1][0] != c && white[1][1] != r));
    }

    public static void humanMove() {
        while (true) {
            String s = sc.next();
            co = column.get(s.charAt(1));
            r = Integer.parseInt(s.substring(2))-1;
            if (isValid(co, r, 2)) {
                break;
            } else {
                System.out.println("Invalid move");
            }
        }
        if (co == white[1][0] && r == white[1][1]) {
            //вывести поле два короля(черный вместо ладьи)
            StringBuilder sb = new StringBuilder();
            for (int i = 7; i >= 0; i--) { //r
                sb.append((1+i) + " ");
                for (int j = 0; j < 8; j++) { //c
                    if (i == white[0][1] && j == white[0][0]) {
                        sb.append(" WK");
                    } else if (i == white[1][1] && j == white[1][0]) {
                        sb.append(" BK");
                    } else if ((i+j) % 2 == 0) {
                        sb.append(" II");
                    } else {
                        sb.append(" __");
                    }
                }
                sb.append("\n");
            }
            sb.append("  ");
            for (Map.Entry t : column.entrySet()){
                sb.append("  " + t.getKey()); 
            }
            System.out.println(sb);
            sb = new StringBuilder();
            System.out.println("White's move");
            // вывести поле белый король(вместо ладьи)
            for (int i = 7; i >= 0; i--) { //r
                sb.append((1+i) + " ");
                for (int j = 0; j < 8; j++) { //c
                    if (i == white[1][1] && j == white[1][0]) {
                        sb.append(" WK");
                    } else if ((i+j) % 2 == 0) {
                        sb.append(" II");
                    } else {
                        sb.append(" __");
                    }
                }
                sb.append("\n");
            }
            sb.append("  ");
            for (Map.Entry t : column.entrySet()){
                sb.append("  " + t.getKey()); 
            }
            System.out.println(sb);
            finish = true;
        } else {
            blackKing[0] = co;
            blackKing[1] = r;
            //вывести поле
            StringBuilder sb = new StringBuilder();
            for (int i = 7; i >= 0; i--) { //r
                sb.append((1+i) + " ");
                for (int j = 0; j < 8; j++) { //c
                    if (i == blackKing[1] && j == blackKing[0]) {
                        sb.append(" BK");
                    } else if (i == white[0][1] && j == white[0][0]) {
                        sb.append(" WK");
                    } else if (i == white[1][1] && j == white[1][0]) {
                        sb.append(" WR");
                    } else if ((i+j) % 2 == 0) {
                        sb.append(" II");
                    } else {
                        sb.append(" __");
                    }
                }
                sb.append("\n");
            }
            sb.append("  ");
            for (Map.Entry t : column.entrySet()){
                sb.append("  " + t.getKey()); 
            }
            System.out.println(sb);
        }
    }

    public static void computerMove() {
        int king = 0;
        if (white[0][1] != 0 && blackKing[1]-1 == white[0][1] && (blackKing[0] == white[0][0] || blackKing[0]+1 == white[0][0] || blackKing[0]-1 == white[0][0])) {
            king = 2;
        } else if (white[1][1] == blackKing[1] || white[1][0] == blackKing[0]) {
            king = 3;
        } else if (blackKing[0] == white[0][0] && blackKing[1] == white[0][1]+2 && white[1][1]+1 == blackKing[1]) {
            co = white[1][0];
            r = white[1][1]+1;
            //rook
        } else if (white[0][1] != 0 && blackKing[1]-1 == white[0][1] && blackKing[0] != white[0][0] && blackKing[0]+1 != white[0][0] && blackKing[0]-1 != white[0][0]) {
            co = white[0][0];
            r = white[0][1]-1;
            king = 1;
        }  else if (blackKing[1] != white[1][1]+1 && (blackKing[0] >= white[0][0] && white[0][0] > white[1][0] || blackKing[0] <= white[0][0] && white[0][0] < white[1][0]) && blackKing[0] != white[1][0] && blackKing[0]+1 != white[1][0] && blackKing[0]-1 != white[1][0]) {
            co = white[1][0];
            r = blackKing[1]-1;
            //rook
        }  else if (white[0][1] == white[1][1]) {
            if (white[0][0] < white[1][0]) {
                co = 7;
                r = white[1][1];
            } else {
                co = 0;
                r = white[1][1];
            }
            //rook
        } else if (blackKing[1] != white[1][1]+1 && blackKing[0] >= white[0][0] && white[0][0] <= white[1][0] && white[0][0] != 0) {
            co = 0;
            r = white[1][1];
            //rook
        } else if (blackKing[1] != white[1][1]+1 && blackKing[0] >= white[0][0] && white[0][0] <= white[1][0]) {
            if (blackKing[1]-2 == white[0][1]) {
                co = 1;
                r = white[0][1];
            } else {
                co = 1;
                r = white[0][1]+1;
            }
            king = 1;
        } else if (blackKing[1] != white[1][1]+1 && white[0][0] != 7) {
            co = 7;
            r = white[1][1];
            //rook
        } else if (blackKing[1] != white[1][1]+1) {
            if (blackKing[1]-2 == white[0][1]) {
                co = 6;
                r = white[0][1];
            } else {
                co = 6;
                r = white[0][1]+1;
            }
            king = 1;
        } else if (blackKing[1] != white[0][1]+2) {
            co = white[0][0];
            r = white[0][1] +1;
            king = 1;
        } else if (blackKing[0]+1 > white[0][0]) {
            co = white[0][0]+1;
            r = white[0][1];
            king = 1;
        } else if (blackKing[0]+1 == white[0][0]) {
            co = white[0][0];
            r = white[0][1]+1;
            //rook
        } else if (blackKing[0]+1 < white[0][0]){
            co = white[0][0]-1;
            r = white[0][1];
            king = 1;
        } else if (blackKing[0]+1 == white[0][0]){
            co = white[0][0];
            r = white[0][1]+1;
            //rook
        } else if (blackKing[0] > white[1][0]) {
            co = blackKing[0]-1;
            r = white[1][1];
            //rook
        } else {
            co = blackKing[0]+1;
            r = white[1][1];
            //rook
        }
        if (king < 2) {
            if (king == 1) {
                white[0][0] = co;
                white[0][1] = r;
            } else {
                white[1][0] = co;
                white[1][1] = r;
            }
            System.out.println("-------Move: " + (co+1) + " " + (r+1) + " ----------------------------------------");

            StringBuilder sb = new StringBuilder();
            for (int i = 7; i >= 0; i--) { //r
                sb.append((1+i) + " ");
                for (int j = 0; j < 8; j++) { //c
                    if (i == blackKing[1] && j == blackKing[0]) {
                        sb.append(" BK");
                    } else if (i == white[0][1] && j == white[0][0]) {
                        sb.append(" WK");
                    } else if (i == white[1][1] && j == white[1][0]) {
                        sb.append(" WR");
                    } else if ((i+j) % 2 == 0) {
                        sb.append(" II");
                    } else {
                        sb.append(" __");
                    }
                }
                sb.append("\n");
            }
            sb.append("  ");
            for (Map.Entry t : column.entrySet()){
                sb.append("  " + t.getKey()); 
            }
            System.out.println(sb);
            //вывести поле
        } else if (king == 2) {
            finish = true;
            System.out.println("-------Move: " + blackKing[0] + " " + blackKing[1] + " ----------------------------------------");
            StringBuilder sb = new StringBuilder();
            for (int i = 7; i >= 0; i--) { //r
                sb.append((1+i) + " ");
                for (int j = 0; j < 8; j++) { //c
                    if (i == blackKing[1] && j == blackKing[0]) {
                        sb.append(" WK");
                    } else if (i == white[1][1] && j == white[1][0]) {
                        sb.append(" WR");
                    } else if ((i+j) % 2 == 0) {
                        sb.append(" II");
                    } else {
                        sb.append(" __");
                    }
                }
                sb.append("\n");
            }
            sb.append("  ");
            for (Map.Entry t : column.entrySet()){
                sb.append("  " + t.getKey()); 
            }
            System.out.println(sb);
        } else {
            finish = true;
            System.out.println("-------Move: " + (co+1) + " " + (r+1) + " ----------------------------------------");

            StringBuilder sb = new StringBuilder();
            for (int i = 7; i >= 0; i--) { //r
                sb.append((1+i) + " ");
                for (int j = 0; j < 8; j++) { //c
                    if (i == blackKing[1] && j == blackKing[0]) {
                        sb.append(" WR");
                    } else if (i == white[0][1] && j == white[0][0]) {
                        sb.append(" WK");
                    } else if ((i+j) % 2 == 0) {
                        sb.append(" II");
                    } else {
                        sb.append(" __");
                    }
                }
                sb.append("\n");
            }
            sb.append("  ");
            for (Map.Entry t : column.entrySet()){
                sb.append("  " + t.getKey()); 
            }
            System.out.println(sb);
        }
    }
	public static void main(String[] args) {
        //изначально может ладья сьесть
        column.put('a', 0);
        column.put('b', 1);
        column.put('c', 2);
        column.put('d', 3);
        column.put('e', 4);
        column.put('f', 5);
        column.put('g', 6);
        column.put('h', 7);
        finish = false;
        sc = new Scanner(System.in);
        System.out.println("Let's play!");
        System.out.println("Examples of position: c8, a2");
        System.out.println("Enter start position of white king");
        char c;
        String wK = sc.next();
        int[] whiteKing = new int[2];
        c = wK.charAt(0);
        whiteKing[0] = column.get(c);
        whiteKing[1] = Integer.parseInt(wK.substring(1))-1;
        System.out.println("Enter start position of white rook");
        String wR = sc.next();
        int[] whiteRook = new int[2];
        c = wR.charAt(0);
        whiteRook[0] = column.get(c);
        whiteRook[1] = Integer.parseInt(wR.substring(1))-1;
        System.out.println("Enter start position of black king");
        String bK = sc.next();
        blackKing = new int[2];
        c = bK.charAt(0);
        blackKing[0] = column.get(c);
        blackKing[1] = Integer.parseInt(bK.substring(1))-1;
        System.out.println("Examples of king's move: Kc8, Ka2. Examples of rook's move: Rc8, Ra2" );
        white = new int[2][2];
        white[0] = whiteKing;
        white[1] = whiteRook;
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i >= 0; i--) { //r
            sb.append((1+i) + " ");
            for (int j = 0; j < 8; j++) { //c
                if (i == blackKing[1] && j == blackKing[0]) {
                    sb.append(" BK");
                } else if (i == white[0][1] && j == white[0][0]) {
                    sb.append(" WK");
                } else if (i == white[1][1] && j == white[1][0]) {
                    sb.append(" WR");
                } else if ((i+j) % 2 == 0) {
                    sb.append(" II");
                } else {
                    sb.append(" __");
                }
            }
            sb.append("\n");
        }
        sb.append("  ");
        for (Map.Entry t : column.entrySet()){
            sb.append("  " + t.getKey()); 
        }
        System.out.println(sb);
        if (blackKing[1] > whiteKing[1]) {
            while (!finish) {
                System.out.println("White's move");
                computerMove();
                if (finish) {
                    break;
                }
                System.out.println("Black, make move");
                humanMove();
            }
        }
        System.out.println("End of game: WINNER is white");
    }
}