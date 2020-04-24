package lorikeet.core;

import java.util.Objects;

public class OkResult<T, E extends Exception> extends BasicOkResult<T, E> {

    private final T value;

    public OkResult(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return this.value;
    }

    public FallibleStream.FS1<T, E> stream() {
        return new FallibleStream<E>()
            .include(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        final OkResult<?, ?> that = (OkResult<?, ?>)o;

        return Objects.equals(this.value(), that.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value());
    }
}