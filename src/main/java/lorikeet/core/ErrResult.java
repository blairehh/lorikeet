package lorikeet.core;

import java.util.Objects;

public class ErrResult<T, E extends Exception> extends BasicErrResult<T, E> {

    private final Seq<? extends E> exceptions;

    public ErrResult(E exception) {
        this.exceptions = new SeqOf<>(exception);
    }

    public ErrResult(ErrResult<?, E> err) {
        this.exceptions = err.exceptions;
    }

    @Override
    public E exception() {
        return this.exceptions.first().orElseThrow();
    }

    @Override
    public Seq<? extends E> errors() {
        return this.exceptions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        final ErrResult<?, ?> err = (ErrResult<?, ?>)o;

        return Objects.equals(this.exception(), err.exception());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.exception());
    }

}