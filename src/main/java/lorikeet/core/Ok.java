package lorikeet.core;

import java.util.Objects;

public class Ok<T> extends BasicOk<T> {

    private final T value;

    public Ok(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return this.value;
    }

    public FallibleStream.FS1<T, Exception> stream() {
        return new FallibleStream<>()
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

        final Ok<?> that = (Ok<?>)o;

        return Objects.equals(this.value(), that.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value());
    }
}