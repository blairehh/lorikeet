package lorikeet.core;

import java.util.function.Consumer;

public interface Fallible<T> {
    boolean success();
    boolean failure();

    T orGive(T value);
    T orPanic();



    Fallible<T> onSuccess(Consumer<T> consumer);
    Fallible<T> onFailure(Consumer<Exception> consumer);
}