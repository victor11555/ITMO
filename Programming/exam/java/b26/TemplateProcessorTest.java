import java.io.File;

public class TemplateProcessorTest {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Ожидаются два аргумента");
        }
        File subs = new File(args[0]);
        File template = new File(args[1]);
        TemplateProcessor tp = new TemplateProcessor(subs);
        tp.process(template);
    }
}
