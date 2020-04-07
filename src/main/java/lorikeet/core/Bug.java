package lorikeet.core;

import java.util.Objects;

public class Bug<T> implements AnErr<T> {
    private final Exception exception;

    public Bug(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception exception() {
        return this.exception;
    }

    @Override
    public Seq<? extends Exception> errors() {
        return new SeqOf<>(this.exception);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        final Bug<?> err = (Bug<?>)o;

        return Objects.equals(this.exception(), err.exception());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.exception());
    }
}
