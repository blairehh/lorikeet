package lorikeet.core;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class OptOf<T> implements Fallible<T> {
    private final Optional<T> optional;
    private final Exception exception;

    public OptOf(Optional<T> optional) {
        this.optional = optional;
        this.exception = new RuntimeException("replace me with a better exception");
    }

    @Override
    public boolean success() {
        return this.optional.isPresent();
    }

    @Override
    public boolean failure() {
        return this.optional.isEmpty();
    }

    @Override
    public Seq<? extends Exception> errors() {
        if (this.optional.isEmpty()) {
            return new SeqOf<>(new NoSuchElementException());
        }
        return new SeqOf<>();
    }

    @Override
    public T orGive(T value) {
        return this.optional.orElse(value);
    }

    @Override
    public T orGive(Function<Exception, T> giver) {
        return this.optional.orElseGet(() -> giver.apply(this.exception));
    }

    @Override
    public T orGive(Supplier<T> supplier) {
        return this.optional.orElseGet(supplier);
    }

    @Override
    public T orPanic() {
        return this.optional.orElseThrow();
    }

    @Override
    public <X> Fallible<X> map(Function<T, X> then) {
        return new OptOf<>(this.optional.map(then));
    }

    @Override
    public <X> Fallible<X> then(Function<T, Fallible<X>> then) {
        if (this.optional.isPresent()) {
            return then.apply(this.optional.orElseThrow());
        }
        return new Err<>(this.exception);
    }

    @Override
    public Fallible<T> onSuccess(Consumer<T> consumer) {
        this.optional.ifPresent(consumer);
        return this;
    }

    @Override
    public boolean hasError(Exception exception) {
        if (this.optional.isPresent()) {
            return false;
        }
        return new NoSuchElementException().equals(exception);
    }

    @Override
    public boolean hasError(Class<? extends Exception> exceptionType) {
        if (this.optional.isPresent()) {
            return false;
        }
        return NoSuchElementException.class.equals(exceptionType);
    }

    @Override
    public Fallible<T> onFailure(Consumer<Exception> consumer) {
        if (this.optional.isEmpty()) {
            consumer.accept(this.exception);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        OptOf<?> optOf = (OptOf<?>) o;

        return Objects.equals(this.optional, optOf.optional)
            && Objects.equals(this.exception, optOf.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.optional, this.exception);
    }
}
