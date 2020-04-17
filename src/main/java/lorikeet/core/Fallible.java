package lorikeet.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

// @TODO
// A) always return a seq of errors
// B) make AnOk and AnErr an abstract class
public interface Fallible<T> {

    boolean success();
    boolean failure();
    Seq<? extends Exception> errors();

    T orGive(T value);
    T orGive(Function<Exception, T> giver);
    T orGive(Supplier<T> supplier);
    T orPanic();

    <X> Fallible<X> map(Function<T, X> then);
    <X> Fallible<X> then(Function<T, Fallible<X>> then);

    boolean hasError(Exception exception);
    boolean hasError(Class<? extends Exception> exceptionType);

    Fallible<T> onSuccess(Consumer<T> consumer);
    Fallible<T> onFailure(Consumer<Exception> consumer);
}