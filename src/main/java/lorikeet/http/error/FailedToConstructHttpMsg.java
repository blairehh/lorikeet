package lorikeet.http.error;

import java.util.Objects;

public class FailedToConstructHttpMsg extends RuntimeException {
    private final Class<?> klass;

    public FailedToConstructHttpMsg(Class<?> klass, Throwable cause) {
        super(String.format("Failed to construct instance of '%s'", klass.getCanonicalName()), cause);
        this.klass = klass;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        FailedToConstructHttpMsg that = (FailedToConstructHttpMsg) o;

        return Objects.equals(this.klass, that.klass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.klass);
    }
}
