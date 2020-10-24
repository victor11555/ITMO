package markup;
import java.util.List;

public class Strikeout extends AP {

    public Strikeout(List<Mark> list) {
        super(list);
        super.mrk = "~";
        super.tex1 = "\\textst{";
        super.tex2 = "}";
    }   
}