package nodes;

import parser.BBCodeParser;

public class Size extends BBNode {
    private final String size;
    public Size(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
        if (tag.attr.startsWith("\"")) {
            this.size = tag.attr.substring(1, tag.attr.length()-2);
        } else {
            this.size = tag.attr + "px";
        }
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder();
        sb.append("<span style=\"font-size: ").append(this.size).append(";\">");
        for (BBNode bbNode : this.subnodes) {
            sb.append(bbNode.html());
        }
        sb.append("</span>");
        return sb.toString();
    }
}
