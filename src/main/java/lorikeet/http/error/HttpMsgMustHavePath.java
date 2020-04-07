package lorikeet.http.error;

import java.util.Objects;

public class HttpMsgMustHavePath extends RuntimeException {
    private final Class<?> msgClass;

    public HttpMsgMustHavePath(Class<?> msgClass) {
        super(String.format("Msg type '%s' must be annotated with @Path", msgClass.getCanonicalName()));
        this.msgClass = msgClass;
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
