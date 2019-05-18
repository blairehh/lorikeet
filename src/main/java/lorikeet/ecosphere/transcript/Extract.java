package lorikeet.ecosphere.transcript;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class Extract<T> {
    private final T value;
    private final int continuation;
    private final Exception error;

    public Extract(T value, int continuation) {
        this.value = value;
        this.continuation = continuation;
        this.error = null;
    }

    public Extract(Exception error) {
        this.value = null;
        this.continuation = 0;
        this.error = error;
    }

    public T getValue() {
        return this.value;
    }

    public int getContinuation() {
        return this.continuation;
    }

    public Exception getError() {
        return this.error;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Extract<?> extract = (Extract<?>) o;

        return Objects.equals(this.getContinuation(), extract.getContinuation())
            && Objects.equals(this.getValue(), extract.getValue())
            && Objects.equals(this.getError(), extract.getError());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValue(), this.getContinuation(), this.getError());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("value", value)
            .append("continuation", continuation)
            .append("error", error)
            .toString();
    }
}
