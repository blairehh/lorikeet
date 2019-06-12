package lorikeet.error;

import java.util.Objects;

public class LorikeetException extends RuntimeException {

    private final Class<?> klass;

    protected LorikeetException(Class<?> klass) {
        this.klass = klass;
    }

    protected LorikeetException(Exception cause, Class<?> klass) {
        super(cause);
        this.klass = klass;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.klass.equals(o.getClass())) {
            return false;
        }

        LorikeetException that = (LorikeetException) o;

        return Objects.equals(this.klass, that.klass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.klass);
    }
}
