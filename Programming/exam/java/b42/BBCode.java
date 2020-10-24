import nodes.BBNode;
import parser.BBCodeParser;

import java.io.InputStream;
import java.util.List;

public class BBCode {
    private List<BBNode> nodes;

    public BBCode(InputStream is) {
        this.nodes = new BBCodeParser(is).parse();
    }

    public String html() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        for (BBNode node : nodes) {
            sb.append(node.html());
        }
        sb.append("</body>\n");
        sb.append("</html>");
        return sb.toString();
    }
}
