package markup;
import java.util.List;


public class Emphasis extends AP {
   
    public Emphasis(List<Mark> list) {
        super(list);
        super.mrk = "*";
        super.tex1 = "\\emph{";
        super.tex2 = "}";
    }   
}