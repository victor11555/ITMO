package nodes;

import parser.BBCodeParser;

public class Color extends BBNode {
    private final String color;
    public Color(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
        if (tag.attr.startsWith("\"")) {
            this.color = tag.attr.substring(1, tag.attr.length() - 2);
        } else {
            this.color = tag.attr;
        }
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder();
        sb.append("<span style=\"color: ").append(this.color).append(";\">");
        for (BBNode bbNode : this.subnodes) {
            sb.append(bbNode.html());
        }
        sb.append("</span>");
        return sb.toString();
    }
}
