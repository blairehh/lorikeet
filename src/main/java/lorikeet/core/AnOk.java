package lorikeet.core;

import java.util.function.Consumer;

public interface AnOk<T, S extends Fallible<T, S>> extends Fallible<T, S> {


    S self();
    T value();

    default public boolean success() {
        return true;
    }

    default public boolean failure() {
        return false;
    }

    default public T orPanic() {
        return this.value();
    }

    default public T orGive(T value) {
        return this.value();
    }

    default public S onSuccess(Consumer<T> consumer) {
        consumer.accept(this.value());
        return this.self();
    }

    default public S onFailure(Consumer<Exception> consumer) {
        return this.self();
    }
}