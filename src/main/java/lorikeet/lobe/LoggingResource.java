package lorikeet.lobe;

public interface LoggingResource {
    void log(LogGrade grade, String fmt, Object... vars);
}