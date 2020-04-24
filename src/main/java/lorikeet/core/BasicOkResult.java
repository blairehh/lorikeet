package lorikeet.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BasicOkResult<T, E extends Exception> implements FallibleResult<T, E> {

    public abstract T value();

    @Override
    public boolean success() {
        return true;
    }

    @Override
    public boolean failure() {
        return false;
    }

    @Override
    public Seq<? extends E> errors() {
        return new SeqOf<>();
    }

    @Override
    public T orPanic() {
        return this.value();
    }

    @Override
    public T orGive(T value) {
        return this.value();
    }

    @Override
    public T orGive(Function<E, T> giver) {
        return this.value();
    }

    @Override
    public T orGive(Supplier<T> supplier) {
        return this.value();
    }

    @Override
    public <X> BasicOkResult<X, E> map(Function<T, X> then) {
        return new OkResult<>(then.apply(this.value()));
    }

    @Override
    public <X> FallibleResult<X, E> proceed(Function<T, FallibleResult<X, E>> then) {
        return then.apply(this.value());
    }

    @Override
    public boolean hasError(E exception) {
        return false;
    }

    @Override
    public boolean hasError(Class<? extends E> exceptionType) {
        return false;
    }

    @Override
    public BasicOkResult<T, E> onSuccess(Consumer<T> consumer) {
        consumer.accept(this.value());
        return this;
    }

    @Override
    public BasicOkResult<T, E> onFailure(Consumer<E> consumer) {
        return this;
    }
}