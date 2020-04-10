package lorikeet.http.error;

import java.util.Objects;

public class HttpMsgMustHaveMethod extends RuntimeException {
    private final Class<?> msgClass;

    public HttpMsgMustHaveMethod(Class<?> msgClass) {
        super(String.format("Msg type '%s' must be annotated with http method (e.g. @GET, @PUT)", msgClass));
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

        HttpMsgMustHaveMethod that = (HttpMsgMustHaveMethod) o;

        return Objects.equals(this.msgClass, that.msgClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msgClass);
    }
}
