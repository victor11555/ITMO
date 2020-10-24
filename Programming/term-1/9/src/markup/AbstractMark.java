package markup;

import java.util.List;

abstract class AbstractMark implements TextTool {
    protected List<TextTool> text;
    protected String mark;
    protected String left, right;

    protected AbstractMark(List<TextTool> text) {
        this.text = text;
    }

    public void toMarkdown(StringBuilder string) {
        string.append(mark);
        for (TextTool i : this.text) {
            i.toMarkdown(string);
        }
        string.append(mark);
    }

    public void toHtml(StringBuilder str) {
        str.append(left);
        for (TextTool i : this.text) {
            i.toHtml(str);
        }
        str.append(right);
    }
}