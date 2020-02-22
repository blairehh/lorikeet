package lorikeet.core;

import java.util.function.Consumer;

public interface AnOk<T> extends Fallible<T> {

    T value();

    @Override
    default boolean success() {
        return true;
    }

    @Override
    default boolean failure() {
        return false;
    }

    @Override
    default T orPanic() {
        return this.value();
    }

    @Override
    default T orGive(T value) {
        return this.value();
    }

    @Override
    default AnOk<T> onSuccess(Consumer<T> consumer) {
        consumer.accept(this.value());
        return this;
    }

    @Override
    default AnOk<T> onFailure(Consumer<Exception> consumer) {
        return this;
    }
}