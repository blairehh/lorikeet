package lorikeet.lobe;

public class DefaultTract<R extends UsesLogging> implements Tract<R> {

    private final R resources;

    public DefaultTract(R resources) {
        this.resources = resources;
    }

    @Override
    public <O> O write(LorikeetWrite<R, O> write) {
        return write.junction(this.resources);
    }

    @Override
    public void log(String fmt, Object...vars) {
        this.resources.useLogging().log(LogGrade.INFO, fmt, vars);
    }
}