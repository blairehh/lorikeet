package lorikeet.core;

import java.util.function.Consumer;
import java.util.function.Function;

public interface AnErr<T> extends Fallible<T> {

    Exception exception();

    @Override
    default  boolean success() {
        return false;
    }

    @Override
    default boolean failure() {
        return false;
    }

    @Override
    default T orPanic() {
        throw new PanickedException(this.exception());
    }

    @Override
    default T orGive(T value) {
        return value;
    }

    @Override
    default T orGive(Function<Exception, T> giver) {
        return giver.apply(this.exception());
    }

    @Override
    default <X> AnErr<X> map(Function<T, X> then) {
        return new Err<>(this.exception());
    }

    @Override
    default <X> Fallible<X> then(Function<T, Fallible<X>> then) {
        return new Err<>(this.exception());
    }

    @Override
    default AnErr<T> onSuccess(Consumer<T> consumer) {
        return this;
    }

    @Override
    default AnErr<T> onFailure(Consumer<Exception> consumer) {
        consumer.accept(this.exception());
        return this;
    }
}