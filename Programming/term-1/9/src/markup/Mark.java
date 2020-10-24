package markup;

import java.util.List;

public class Mark extends AbstractMark {
    public Mark(List<TextTool> text) {
        super(text);
        super.mark = "~";
        super.left = "<mark>";
        super.right = "</mark>";
    }
}