package lorikeet.lobe;

public class StdOutLoggingResource implements LoggingResource {
    
    @Override
    public void log(LogGrade grade, String fmt, Object... vars) {
        System.out.println(String.format(fmt, vars));
    }
}