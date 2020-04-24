package lorikeet.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BasicErr<T> implements Fallible<T> {

    public abstract Exception exception();

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
    public T orGive(Function<Exception, T> giver) {
        return giver.apply(this.exception());
    }

    @Override
    public T orGive(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public <X> BasicErr<X> map(Function<T, X> then) {
        return new Err<>(this.exception());
    }

    @Override
    public <X> Fallible<X> then(Function<T, Fallible<X>> then) {
        return new Err<>(this.exception());
    }

    @Override
    public <X> FallibleResult<X, Exception> proceed(Function<T, FallibleResult<X, Exception>> then) {
        return new ErrResult<>(this.exception());
    }

    @Override
    public boolean hasError(Exception exception) {
        return this.errors().contains(exception);
    }

    @Override
    public boolean hasError(Class<? extends Exception> exceptionType) {
        return this.errors().stream().anyMatch((ex) -> ex.getClass().equals(exceptionType));
    }

    @Override
    public BasicErr<T> onSuccess(Consumer<T> consumer) {
        return this;
    }

    @Override
    public BasicErr<T> onFailure(Consumer<Exception> consumer) {
        consumer.accept(this.exception());
        return this;
    }
}