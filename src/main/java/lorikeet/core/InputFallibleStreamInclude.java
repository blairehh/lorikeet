package lorikeet.core;

public interface InputFallibleStreamInclude<I, O, E extends Exception> {
    FallibleResult<O, E> include(I input);
}
