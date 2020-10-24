package nodes;

import parser.BBCodeParser;

import java.util.ArrayList;
import java.util.List;

public abstract class BBNode {
    protected List<BBNode> subnodes;

    BBNode() {
        this.subnodes = new ArrayList<>();
    }

    BBNode(BBCodeParser parser, BBCodeParser.OpenTag tag) {
        this.subnodes = parser.parseInner(tag.tagName);
    }
    public abstract String html();
}
