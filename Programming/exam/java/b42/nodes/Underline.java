package nodes;

import parser.BBCodeParser;

public class Underline extends BBNode {
    public Underline(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder("<span style=\"text-decoration: underline;\">");
            for (BBNode subnode : this.subnodes) {
                sb.append(subnode.html());
            }
        return sb.append("</span>").toString();
    }
}
