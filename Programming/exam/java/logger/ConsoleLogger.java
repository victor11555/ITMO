import java.util.*;

public class ConsoleLogger implements Logger{
    
    @Override
    public void log(String message){
        System.out.println(message);
    }

    @Override
    public void log(String message, Throwable cause) {
        Exception e = new Exception(message, cause);
        System.out.println(e);
    }

    @Override
    public void timeLog(String message){
        Date date = new Date();
        System.out.println(date + ":  " + message);
    }

    private Log priority;
    private static final Map<Log, Integer> priorityLevels = Map.of(Log.DEBUG, 0, Log.INFO,
            1, Log.WARNING, 2, Log.ERROR, 3);

    public void setPriority(Log priority){ this.priority = priority; }

    public Log getPriority(){
        return this.priority;
    }

    public void priorityLog(String message, Log priority){
        int numPriority = priorityLevels.get(priority);
        if (priorityLevels.get(this.priority) >= numPriority){
            log(message);
        }
    }
}
