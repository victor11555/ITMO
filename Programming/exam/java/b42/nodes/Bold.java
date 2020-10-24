package nodes;

import parser.BBCodeParser;

public class Bold extends BBNode {

    public Bold(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder("<strong>");
        for (BBNode subnode : this.subnodes) {
            sb.append(subnode.html());
        }
        return sb.append("</strong>").toString();
    }
}
