package nodes;

import parser.BBCodeParser;

import java.util.ArrayList;
import java.util.List;

public class BBList extends BBNode {
    private final BBCodeParser.OpenTag tag;
    private List<String> items = new ArrayList<>();
    public BBList(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        super(parser, tag);
        this.tag = tag;
        if (this.subnodes.size() == 1) {
            String str = this.subnodes.remove(0).html();
            String[] items = str.split("\n");
            for (String item : items) {
                item = item.trim();
                if (item.isEmpty()) continue;
                item = item.substring(1);
                this.items.add(item.trim());
            }
        }
    }

    @Override
    public String html() {
        StringBuilder sb = new StringBuilder();
        if (this.items.isEmpty()) {
            sb.append("<").append(this.tag.tagName).append(">");
            for (BBNode bbnode: this.subnodes) {
                sb.append(bbnode.html());
            }
            sb.append("</").append(this.tag.tagName).append(">");
        } else {
            sb.append("<ul>");
            for (String item : this.items) {
                sb.append("<li>").append(item).append("</li>");
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }

    public static class ListItem extends BBNode {
        public ListItem(BBCodeParser parser, BBCodeParser.OpenTag tag) {
            super(parser, tag);
        }

        @Override
        public String html() {
            StringBuilder sb = new StringBuilder();
            sb.append("<li>");
            for (BBNode bbNode : this.subnodes) {
                sb.append(bbNode.html());
            }
            sb.append("</li>");
            return sb.toString();
        }
    }
}
