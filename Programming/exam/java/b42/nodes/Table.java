package nodes;

import parser.BBCodeParser;

public class Table extends BBNode {
    public Table(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        for (BBNode bbNode : this.subnodes) {
            sb.append(bbNode.html());
        }
        sb.append("</table>");
        return sb.toString();
    }

    public static class TableRow extends BBNode {
        public TableRow(BBCodeParser parser, BBCodeParser.OpenTag tag) {
            super(parser, tag);
        }

        @Override
        public String html() {
            StringBuilder sb = new StringBuilder();
            sb.append("<tr>");
            for (BBNode bbNode : this.subnodes) {
                sb.append(bbNode.html());
            }
            sb.append("</tr>");
            return sb.toString();
        }
    }

    public static class TableCell extends BBNode {
        public TableCell(BBCodeParser parser, BBCodeParser.OpenTag tag) {
            super(parser, tag);
        }

        @Override
        public String html() {
            StringBuilder sb = new StringBuilder();
            sb.append("<td>");
            for (BBNode bbNode : this.subnodes) {
                sb.append(bbNode.html());
            }
            sb.append("</td>");
            return sb.toString();
        }
    }
}
