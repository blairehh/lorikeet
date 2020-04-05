package lorikeet.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    Fallible<T> onSuccess(Consumer<T> consumer);
    Fallible<T> onFailure(Consumer<Exception> consumer);
}