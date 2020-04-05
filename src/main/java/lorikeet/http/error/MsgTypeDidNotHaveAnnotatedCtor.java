package lorikeet.http.error;

import java.util.Objects;

public class MsgTypeDidNotHaveAnnotatedCtor extends RuntimeException {
    private final Class<?> klass;
    public MsgTypeDidNotHaveAnnotatedCtor(Class<?> klass) {
        super(String.format("Type '%s' did not have constructor annotated with @MsgCtro", klass.getCanonicalName()));
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

        MsgTypeDidNotHaveAnnotatedCtor that = (MsgTypeDidNotHaveAnnotatedCtor) o;

        return Objects.equals(this.klass, that.klass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.klass);
    }
}
