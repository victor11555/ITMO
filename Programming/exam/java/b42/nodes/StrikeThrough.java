package nodes;

import parser.BBCodeParser;

public class StrikeThrough extends BBNode {
    public StrikeThrough(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder("<span style=\"text-decoration: line-through;\">");
        for (BBNode subnode : this.subnodes) {
            sb.append(subnode.html());
        }
        return sb.append("</span>").toString();
    }
}
