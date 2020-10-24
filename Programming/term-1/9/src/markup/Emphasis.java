package markup;

import java.util.List;

public class Emphasis extends AbstractMark {
    public Emphasis(List<TextTool> text) {
        super(text);
        super.mark = "*";
        super.left = "<em>";
        super.right = "</em>";
    }
}