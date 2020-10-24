package markup;

import java.util.List;

public class Paragraph implements TextTool {
    List<TextTool> text;

    public Paragraph(List<TextTool> text) {
        this.text = text;
    }

    public void toMarkdown(StringBuilder str) {
        for (TextTool i : this.text) {
            i.toMarkdown(str);
        }
    }

    public void toHtml(StringBuilder str) {
        str.append("<p>");
        for (TextTool i : this.text) {
            i.toHtml(str);
        }
        str.append("</p>");
    }
}