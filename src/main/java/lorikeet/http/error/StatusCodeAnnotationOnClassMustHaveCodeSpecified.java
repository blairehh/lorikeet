package lorikeet.http.error;

import java.util.Objects;

public class StatusCodeAnnotationOnClassMustHaveCodeSpecified extends RuntimeException {
    private final Class<?> msgType;

    public StatusCodeAnnotationOnClassMustHaveCodeSpecified(Class<?> msgType) {
        super(String.format("Msg type '%s' with @StatusCode on class does not specify code", msgType.getCanonicalName()));
        this.msgType = msgType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        StatusCodeAnnotationOnClassMustHaveCodeSpecified that = (StatusCodeAnnotationOnClassMustHaveCodeSpecified) o;

        return Objects.equals(this.msgType, that.msgType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msgType);
    }
}
