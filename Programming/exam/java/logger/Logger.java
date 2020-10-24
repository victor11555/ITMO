public interface Logger {
    void log(String message);

    void log(String message, Throwable cause);

    void timeLog(String message);
}


