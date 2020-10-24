package markup;

import java.util.List;

public class Code extends AbstractMark {
    public Code(List<TextTool> text) {
        super(text);
        super.mark = "'";
        super.left = "<code>";
        super.right = "</code>";
    }
}