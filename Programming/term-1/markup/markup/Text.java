package markup;
public class Text implements Mark {

    String text;

    public Text(String text){
        this.text = text;
    }


    public StringBuilder toMarkdown(StringBuilder str) {
        return str.append(this.text);
    }

    public StringBuilder toTex(StringBuilder str) {
        return str.append(this.text);
    }

}