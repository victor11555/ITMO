package nodes;

import parser.BBCodeParser;

public class Img extends BBNode {
    public Img(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
        if (this.subnodes.size() < 1) {
            throw new RuntimeException("Ожидается текст в теге 'img'");
        }
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder();
        sb.append("<img src=\"")
                .append(this.subnodes.get(0).html())
                .append("\" alt=\"\">")
                .append("</img>");
        return sb.toString();
    }
}
