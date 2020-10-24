package markup;

import java.util.List;

public class Strikeout extends AbstractMark {
    public Strikeout(List<TextTool> text) {
        super(text);
        super.mark = "~";
        super.left = "<s>";
        super.right = "</s>";
    }
}