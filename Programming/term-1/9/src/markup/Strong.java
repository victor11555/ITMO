package markup;

import java.util.List;

public class Strong extends AbstractMark {
    public Strong(List<TextTool> text) {
        super(text);
        super.mark = "__";
        super.left = "<strong>";
        super.right = "</strong>";
    }
}