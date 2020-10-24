package nodes;

import parser.BBCodeParser;

public class Url extends BBNode {
    private final BBCodeParser.OpenTag openTag;
    public Url(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
        this.openTag = tag;
        if (this.subnodes.size() < 1 && this.openTag.attr == null) {
            throw new RuntimeException("Ожидается текст в теге 'url'");
        }
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder();
        if (this.openTag.attr != null) {
            sb.append("<a href=\"").append(this.openTag.attr).append("\">");
            for (BBNode bbNode : this.subnodes) {
                sb.append(bbNode.html());
            }
            sb.append("</a>");
        } else {
            sb.append("<a href=\"").append(this.subnodes.get(0).html()).append("\">")
              .append(this.subnodes.get(0).html())
              .append("</a>");
        }
        return sb.toString();
    }
}
