package nodes;

import parser.BBCodeParser;

public class Italic extends BBNode {
    public Italic(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder("<span style=\"font-style: italic;\">");
        for (BBNode subnode : this.subnodes) {
            sb.append(subnode.html());
        }
        return sb.append("</span>").toString();
    }
}
