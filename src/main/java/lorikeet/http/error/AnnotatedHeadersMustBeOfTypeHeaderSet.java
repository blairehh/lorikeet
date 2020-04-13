package lorikeet.http.error;

import java.util.Objects;

public class AnnotatedHeadersMustBeOfTypeHeaderSet extends RuntimeException {
    private final Class<?> msgType;

    public AnnotatedHeadersMustBeOfTypeHeaderSet(Class<?> msgType) {
        super(String.format("Annotated with @Headers must be of type HeaderSet in '%s'", msgType.getCanonicalName()));
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

        AnnotatedHeadersMustBeOfTypeHeaderSet that = (AnnotatedHeadersMustBeOfTypeHeaderSet) o;

        return Objects.equals(this.msgType, that.msgType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msgType);
    }
}
