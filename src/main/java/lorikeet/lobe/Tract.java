package lorikeet.lobe;

public interface Tract<R extends UsesLogging> {
    <O> O write(LorikeetWrite<R, O> write);
    void log(String format, Object... vars);
}