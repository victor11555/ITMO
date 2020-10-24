package nodes;

import parser.BBCodeParser;

public class Quote extends BBNode {
    private final BBCodeParser.OpenTag tag;
    public Quote(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        this.tag = tag;
        this.subnodes = parser.parseInner("quote");
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder();
        sb.append("<blockquote><p>");
        for (BBNode bbNode : this.subnodes) {
            sb.append(bbNode.html());
        }
        sb.append("</p></blockquote>");
        return sb.toString();
    }
}
