package lorikeet.core;

import java.util.function.Consumer;

public interface Fallible<T, Self extends Fallible<T, Self>> {
    boolean success();
    boolean failure();

    T orGive(T value);
    T orPanic();



    Self onSuccess(Consumer<T> consumer);
    Self onFailure(Consumer<Exception> consumer);
}