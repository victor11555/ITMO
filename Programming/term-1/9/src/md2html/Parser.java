package md2html;

import markup.*;
import java.util.*;

public class Parser {
    private StringBuilder sb;
    private int curr = 0;
    private int begin;

    public Parser(StringBuilder sb) {
        this.sb = sb;
    }

    private boolean checkMarker() {
        char k = sb.charAt(curr);
        if (!((curr == 0 || Character.isWhitespace(sb.charAt(curr - 1))) &&
                (curr + 1 == sb.length() || Character.isWhitespace(sb.charAt(curr + 1)))) &&
                (curr == 0 || sb.charAt(curr - 1) != '\\')) {
            if (k == '`' || k == '*' || k == '_' || k == '~') {
                return true;
            } else if (k == '-' && (curr < sb.length() - 1) && sb.charAt(curr + 1) == '-') {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void checkSpecial() {
        if (sb.charAt(curr) == '&') {
            sb.replace(curr, curr + 1, "&amp;");
            curr += 4;
        } else if (sb.charAt(curr) == '>') {
            sb.replace(curr, curr + 1, "&gt;");
            curr += 3;
        } else if (sb.charAt(curr) == '<') {
            sb.replace(curr, curr + 1, "&lt;");
            curr += 3;
        }
        if (curr > 0 && sb.charAt(curr - 1) == '\\') {
            sb.deleteCharAt(curr - 1);
        }
    }

    public TextTool parseText() {
        int level = 0;

        if (Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        while (sb.charAt(curr) == '#') {
            level++;
            curr++;
        }
        if (!Character.isWhitespace(sb.charAt(curr))) {
            level = 0;
            curr = 0;
        }
        if (level > 0) curr++;
        begin = curr;

        List list = new LinkedList<TextTool>();

        while (curr < sb.length()) {
            while (curr < sb.length() && !checkMarker()) {
                checkSpecial();
                curr++;
            }
            if (begin < curr) {
                list.add(new Text(sb.substring(begin, curr)));
            }
            if (curr < sb.length() - 1) {
                list.add(parseChoose());
            }
        }

        if (level > 0) {
            return new Head(list, level);
        } else {
            return new Paragraph(list);
        }
    }

    private TextTool parseChoose() {
        if (sb.charAt(curr) == sb.charAt(curr + 1)) {
            curr += 2;
            begin = curr;
            return parseUnit(sb.charAt(curr - 1), 2);
        } else {
            begin = ++curr;
            return parseUnit(sb.charAt(curr - 1), 1);
        }
    }

    private TextTool parseUnit(char sym, int num) {
        List list = new LinkedList<TextTool>();
        boolean closed = false;

        while (!closed) {
            while (!checkMarker()) {
                checkSpecial();
                curr++;
            }

            if (begin < curr) {
                list.add(new Text(sb.substring(begin, curr)));
            }

            if (num == 1) {
                if (sb.charAt(curr) == sym &&
                        (sym == '`' || !Character.isWhitespace(sb.charAt(curr - 1)))) {
                    begin = ++curr;
                    closed = true;
                } else {
                    list.add(parseChoose());
                }
            } else {
                if (sb.charAt(curr) == sym && sb.charAt(curr + 1) == sym &&
                        !Character.isWhitespace(sb.charAt(curr - 1))) {
                    curr += 2;
                    begin = curr;
                    closed = true;
                } else {
                    list.add(parseChoose());
                }
            }
        }

        if (num == 1) {
            if (sym == '`') {
                return new Code(list);
            } else if (sym == '~') {
                return new Mark(list);
            } else {
                return new Emphasis(list);
            }
        } else {
            if (sym == '-') {
                return new Strikeout(list);
            } else {
                return new Strong(list);
            }
        }
    }
}