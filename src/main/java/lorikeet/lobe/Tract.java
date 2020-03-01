package lorikeet.lobe;

public interface Tract<R extends UsesLogging> {
    <O> O write(WriteAgent<R, O> write);
    <O> O invoke(LorikeetAction<R, O> action);
    void log(String format, Object... vars);

}