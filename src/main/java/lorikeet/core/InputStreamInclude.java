package lorikeet.core;

public interface InputStreamInclude<I, O> {
    O include(I input);
}
