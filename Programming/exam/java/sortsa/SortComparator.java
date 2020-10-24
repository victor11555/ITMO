package ru.ifmo.stddev.zabrovskiy;

import java.util.Comparator;
import java.util.Set;

public class SortComparator implements Comparator<String> {

    private Set<String> args;

    public SortComparator(Set<String> args) {
        this.args = args;
    }

    @Override
    public int compare(String o1, String o2) {
        int i = 0;
        int endi = o1.length();
        int d = 1;
        int j = 0;
        int endj = o2.length();
        if (args.contains("--reverse")) {
            i = endi - 1;
            j = endj - 1;
            endi = endj = 0;
            d = -1;
        }

        if (args.contains("--numeric-sort")) {
            int x = parseInt(o1, i, endi, d);
            int y = parseInt(o2, j, endj, d);
            return x - y;
        }

        if (args.contains("--ignore-leading-blanks")) {
            i = skipWhitespace(o1, i, endi, d);
            j = skipWhitespace(o2, j, endj, d);
        }

        boolean ignoreCase = args.contains("--ignore-case");
        boolean ignoreNonprinting = args.contains("--ignore-nonprinting");
        boolean dictionaryOrder = args.contains("--dictionary-order");

        while (i != endi && j != endj) {
            int res;
            if (ignoreNonprinting) {
                i = skipNonprinting(o1, i, endi, d);
                j = skipNonprinting(o2, j, endj, d);
            }
            if (dictionaryOrder) {
                i = skipNonDictionary(o1, i, endi, d);
                j = skipNonDictionary(o2, j, endj, d);
            }
            if (i == endi || j == endj) {
                break;
            }
            if (ignoreCase) {
                res = Character.toLowerCase(o1.charAt(i)) - Character.toLowerCase(o2.charAt(j));
            } else {
                res = o1.charAt(i) - o2.charAt(j);
            }
            i += d;
            j += d;

            if (res != 0) {
                return res;
            }
        }
        return i == endi ? 1 : -1;
    }

    private int skipNonprinting(String str, int begin, int end, int d) {
        int i;
        for (i = begin; i != end; i += d) {
            if (!Character.isISOControl(str.charAt(i))) {
                break;
            }
        }
        return i;
    }

    private int skipNonDictionary(String str, int begin, int end, int d) {
        int i;
        for (i = begin; i != end; i += d) {
            if (!(Character.isWhitespace(str.charAt(i)) || Character.isLetterOrDigit(str.charAt(i)))) {
                break;
            }
        }
        return i;
    }

    private int skipWhitespace(String str, int begin, int end, int d) {
        int i;
        for (i = begin; i != end; i += d) {
            if (!Character.isWhitespace(str.charAt(i))) {
                break;
            }
        }
        return i;
    }

    private int parseInt(String str, int begin, int end, int d) {
        int i;
        for (i = begin; i != end; i += d) {
            if (!Character.isDigit(str.charAt(i))) {
                break;
            }
        }
        return Integer.parseInt(str.substring(i, end));
    }
}
