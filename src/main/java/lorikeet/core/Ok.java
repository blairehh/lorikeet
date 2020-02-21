package lorikeet.core;

import java.util.Objects;

public class Ok<T> implements AnOk<T, Ok<T>> {

    private final T value;

    public Ok(T value) {
        this.value = value;
    }

    @Override
    public Ok<T> self() {
        return this;
    }

    @Override
    public T value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        final Ok<?> err = (Ok<?>)o;

        return Objects.equals(this.value, err.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }
}