package lorikeet.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

// @TODO
// A) always return a seq of errors
public interface FallibleResult<T, E extends Exception> {

    boolean success();
    boolean failure();
    Seq<? extends E> errors();

    T orGive(T value);
    T orGive(Function<E, T> giver);
    T orGive(Supplier<T> supplier);
    T orPanic();

    <X> FallibleResult<X, E> map(Function<T, X> then);

    <X> FallibleResult<X, E> proceed(Function<T, FallibleResult<X, E>> then);

    boolean hasError(E exception);
    boolean hasError(Class<? extends E> exceptionType);

    FallibleResult<T, E> onSuccess(Consumer<T> consumer);
    FallibleResult<T, E> onFailure(Consumer<E> consumer);
}