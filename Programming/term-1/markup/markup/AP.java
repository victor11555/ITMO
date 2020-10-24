package markup;

import java.util.List;

public abstract class AP implements Mark {
    protected List<Mark> list;
    protected String mrk = "", tex1 = "", tex2 = "";

    protected AP(List<Mark> list) {
        this.list = list;
    }

    public StringBuilder toMarkdown(StringBuilder str) {
        str.append(mrk);
        for (Mark p : this.list) {
            p.toMarkdown(str);
        }
        str.append(mrk);
        return str;
    } 

    public StringBuilder toTex(StringBuilder str) {
        str.append(tex1);
        for (Mark p : this.list) {
            p.toTex(str);
        }
        str.append(tex2);
        return str;
    }

}
