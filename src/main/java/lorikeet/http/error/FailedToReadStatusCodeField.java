package lorikeet.http.error;

import java.util.Objects;

public class FailedToReadStatusCodeField extends RuntimeException {
    private final Class<?> msgType;
    private final String fieldName;

    public FailedToReadStatusCodeField(Class<?> msgType, String fieldName, Throwable cause) {
        super(String.format(
            "Failed to read field '%s' on msg '%s' for status code",
            msgType.getCanonicalName(),
            fieldName
        ), cause);
        this.msgType = msgType;
        this.fieldName = fieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        FailedToReadStatusCodeField that = (FailedToReadStatusCodeField) o;

        return Objects.equals(this.msgType, that.msgType)
            && Objects.equals(this.fieldName, that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msgType, this.fieldName);
    }
}
