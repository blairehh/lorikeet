package lorikeet.core;

import java.util.function.Consumer;

public interface AnErr<T, S extends Fallible<T, S>> extends Fallible<T, S> {
    

    S self();
    Exception exception();

    default public boolean success() {
        return false;
    }

    default public boolean failure() {
        return false;
    }

    default public T orPanic() {
        throw new PanickedException(this.exception());
    }

    default public T orGive(T value) {
        return value;
    }

    default public S onSuccess(Consumer<T> consumer) {
        return this.self();
    }

    default public S onFailure(Consumer<Exception> consumer) {
        consumer.accept(this.exception());
        return this.self();
    }
}