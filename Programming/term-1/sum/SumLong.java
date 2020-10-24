public class SumLong {

    public static void main(String[] args) {
        String num = "";

        for (int i = 0; i < args.length; i++) {
            num += " " + args[i] + " ";
        }
        num = num.toLowerCase();
        long sum = 0L;
        int cj;
        int c;

        for (int j = 0; j < num.length(); j++) {
            c = 0;
            for (; j < num.length() && !Character.isWhitespace(num.charAt(j)); j++) {
                c++;
            }
            cj = j;
            if (c != 0) {
                sum += Long.parseLong(num.substring(j - c, j));
            }
        }
        System.out.println(sum);
    }
}