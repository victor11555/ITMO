package markup;

import java.util.List;

public class Head implements TextTool {
    List<TextTool> text;
    private int level;

    public Head(List<TextTool> text, int level) {
        this.text = text;
        this.level = level;
    }

    public void toMarkdown(StringBuilder str) {
        for (TextTool i : this.text) {
            i.toMarkdown(str);
        }
    }

    public void toHtml(StringBuilder str) {
        str.append("<h" + level + ">");
        for (TextTool i : this.text) { i.toHtml(str); }
        str.append("</h" + level + ">");
    }
}