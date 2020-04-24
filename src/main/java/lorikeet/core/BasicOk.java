package lorikeet.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BasicOk<T> implements Fallible<T> {

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
    public Seq<? extends Exception> errors() {
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
    public T orGive(Function<Exception, T> giver) {
        return this.value();
    }

    @Override
    public T orGive(Supplier<T> supplier) {
        return this.value();
    }

    @Override
    public <X> BasicOk<X> map(Function<T, X> then) {
        return new Ok<>(then.apply(this.value()));
    }

    @Override
    public <X> Fallible<X> then(Function<T, Fallible<X>> then) {
        return then.apply(this.value());
    }

    @Override
    public <X> FallibleResult<X, Exception> proceed(Function<T, FallibleResult<X, Exception>> then) {
        return then.apply(this.value());
    }

    @Override
    public boolean hasError(Exception exception) {
        return false;
    }

    @Override
    public boolean hasError(Class<? extends Exception> exceptionType) {
        return false;
    }

    @Override
    public BasicOk<T> onSuccess(Consumer<T> consumer) {
        consumer.accept(this.value());
        return this;
    }

    @Override
    public BasicOk<T> onFailure(Consumer<Exception> consumer) {
        return this;
    }

    @Override
    public <E extends Exception> FallibleResult<T, E> asResult(Function<Exception, E> errorMapper) {
        return new OkResult<>(this.value());
    }
}