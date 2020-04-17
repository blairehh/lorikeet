package lorikeet.core;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
    default Seq<? extends Exception> errors() {
        return new SeqOf<>();
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
    default T orGive(Function<Exception, T> giver) {
        return this.value();
    }

    @Override
    default T orGive(Supplier<T> supplier) {
        return this.value();
    }

    @Override
    default <X> AnOk<X> map(Function<T, X> then) {
        return new Ok<>(then.apply(this.value()));
    }

    @Override
    default <X> Fallible<X> then(Function<T, Fallible<X>> then) {
        return then.apply(this.value());
    }

    @Override
    default boolean hasError(Exception exception) {
        return false;
    }

    @Override
    default boolean hasError(Class<? extends Exception> exceptionType) {
        return false;
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