import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileLogger implements Logger {

    @Override
    public void log(String message) {
        try{
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), StandardCharsets.UTF_8));
            out.write(message); 
            out.close();
        } catch (IOException e) {
            System.err.println("IOE: " + e.getMessage());
        }
    }

    @Override
    public void log(String message, Throwable cause) {
        try{
            BufferedWriter out = new BufferedWriter(("output.txt"), StandardCharsets.UTF_8));
            Exception e = new Exception(message, cause);
            out.write(e.toString()); 
            out.close();
        } catch (IOException e) {
            System.err.println("IOE: " + e.getMessage());
        }
    }

    @Override
    public void timeLog(String message) {
        Date date = new Date();
        System.out.println(date + ": " + message);
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