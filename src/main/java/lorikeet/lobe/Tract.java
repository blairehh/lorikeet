package lorikeet.lobe;

public interface Tract<R extends UsesLogging> {
    Tract<R> session(TractSession session);
    <O> O encode(EncodeAgent<R, O> encode);
    <O> O decode(DecodeAgent<R, O> decode);
    <O> O write(WriteAgent<R, O> write);
    <O> O invoke(LorikeetAction<R, O> action);
    void log(String format, Object... vars);
}