package nodes;

import parser.BBCodeParser;

public class Code extends BBNode {
    public Code(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder();
        sb.append("<code style=\"white-space: pre;\">");
        for (BBNode bbNode : this.subnodes) {
            sb.append(bbNode.html());
        }
        sb.append("</code>");
        return sb.toString();
    }
}
