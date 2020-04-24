package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class MsgTypeDidNotHaveAnnotatedCtor extends IncomingHttpSgnlError {
    private final Class<?> klass;
    public MsgTypeDidNotHaveAnnotatedCtor(Class<?> klass) {
        super(String.format("Type '%s' did not have constructor annotated with @MsgCtro", klass.getCanonicalName()));
        this.klass = klass;
    }

    @Override
    public HttpStatus rejectStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
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
