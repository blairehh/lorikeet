package lorikeet.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BasicErrResult<T, E extends Exception> implements FallibleResult<T, E> {

    public abstract E exception();

    @Override
    public boolean success() {
        return false;
    }

    @Override
    public boolean failure() {
        return true;
    }

    @Override
    public T orPanic() {
        throw new PanickedException(this.exception());
    }

    @Override
    public T orGive(T value) {
        return value;
    }

    @Override
    public T orGive(Function<E, T> giver) {
        return giver.apply(this.exception());
    }

    @Override
    public T orGive(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public <X> BasicErrResult<X, E> map(Function<T, X> then) {
        return new ErrResult<>(this.exception());
    }

    @Override
    public <X> FallibleResult<X, E> proceed(Function<T, FallibleResult<X, E>> then) {
        return new ErrResult<>(this.exception());
    }

    @Override
    public boolean hasError(E exception) {
        return this.errors().contains(exception);
    }

    @Override
    public boolean hasError(Class<? extends E> exceptionType) {
        return this.errors().stream().anyMatch((ex) -> ex.getClass().equals(exceptionType));
    }

    @Override
    public BasicErrResult<T, E> onSuccess(Consumer<T> consumer) {
        return this;
    }

    @Override
    public BasicErrResult<T, E> onFailure(Consumer<E> consumer) {
        consumer.accept(this.exception());
        return this;
    }
}