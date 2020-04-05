package lorikeet.core;

import java.util.Objects;

public class Err<T> implements AnErr<T> {

    private final Seq<? extends Exception> exceptions;

    public Err(Exception exception) {
        this.exceptions = new SeqOf<>(exception);
    }

    public Err(Err<?> err) {
        this.exceptions = err.exceptions;
    }

    @Override
    public Exception exception() {
        return this.exceptions.first().orElseThrow();
    }

    @Override
    public Seq<? extends Exception> errors() {
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

        final Err<?> err = (Err<?>)o;

        return Objects.equals(this.exception(), err.exception());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.exception());
    }

}