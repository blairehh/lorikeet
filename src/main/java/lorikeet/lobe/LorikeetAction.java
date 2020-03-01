package lorikeet.lobe;

public interface LorikeetAction<R extends UsesLogging, O> {
    O junction(Tract<R> tract);
}
