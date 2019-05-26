package lorikeet.error;

import java.util.Objects;

public class LorikeetException extends RuntimeException {

    private final String code;
    private final Class<?> klass;

    protected LorikeetException(String code, Class<?> klass) {
        this.code = code;
        this.klass = klass;
    }

    public String getCode() {
        return this.code;
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

        return Objects.equals(this.getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCode());
    }
}
