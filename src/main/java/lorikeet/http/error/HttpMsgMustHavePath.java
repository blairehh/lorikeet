package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class HttpMsgMustHavePath extends IncomingHttpSgnlError {
    private final Class<?> msgClass;

    public HttpMsgMustHavePath(Class<?> msgClass) {
        super(String.format("Msg type '%s' must be annotated with @Path", msgClass.getCanonicalName()));
        this.msgClass = msgClass;
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

        HttpMsgMustHavePath that = (HttpMsgMustHavePath) o;

        return Objects.equals(this.msgClass, that.msgClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msgClass);
    }
}
