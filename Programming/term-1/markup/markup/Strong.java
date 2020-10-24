package markup;
import java.util.List;

public class Strong extends AP {

    public Strong(List<Mark> list) {
        super(list);
        super.mrk = "__";
        super.tex1 = "\\textbf{";
        super.tex2 = "}";
    }  
}