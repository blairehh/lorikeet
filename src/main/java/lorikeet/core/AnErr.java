package lorikeet.core;

import java.util.function.Consumer;

public interface AnErr<T> extends Fallible<T> {

    Exception exception();

    @Override
    default  boolean success() {
        return false;
    }

    @Override
    default public boolean failure() {
        return false;
    }

    @Override
    default public T orPanic() {
        throw new PanickedException(this.exception());
    }

    @Override
    default public T orGive(T value) {
        return value;
    }

    @Override
    default public AnErr<T> onSuccess(Consumer<T> consumer) {
        return this;
    }

    @Override
    default public AnErr<T> onFailure(Consumer<Exception> consumer) {
        consumer.accept(this.exception());
        return this;
    }
}