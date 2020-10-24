package nodes;

public class Text extends BBNode {

    private String text;

    public Text(String text) {
        super();
        this.text = text;
    }

    @Override
    public String html() {
        return text;
    }
}
