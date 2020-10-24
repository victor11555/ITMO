import java.util.Map;

public class CompositeLogger  implements Logger{

    @Override
    public void log(String message) {
        Logger f1 = new FileLogger();
        f1.log(message);
        Logger f2 = new ConsoleLogger();
        f2.log(message);
    }

    @Override
    public void log(String message, Throwable cause) {
        Logger f1 = new FileLogger();
        f1.log(message, cause);
        Logger f2 = new ConsoleLogger();
        f2.log(message, cause);
    }

    @Override
    public void timeLog(String message) {
        Logger f1 = new FileLogger();
        f1.timeLog(message);
        Logger f2 = new ConsoleLogger();
        f2.timeLog(message);
    }

}
