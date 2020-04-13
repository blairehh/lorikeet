package lorikeet.http.error;

import java.util.Objects;

public class BadHttpStatusCodeFieldType extends RuntimeException {
    private final Class<?> msgType;
    private final String fieldName;

    public BadHttpStatusCodeFieldType(Class<?> msgType, String fieldName) {
        super(String.format(
            "Field '%s'on msg '%s' is not valid for @StatusCode, must be int or HttpStatus",
            fieldName,
            msgType.getCanonicalName()
        ));
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

        BadHttpStatusCodeFieldType that = (BadHttpStatusCodeFieldType) o;

        return Objects.equals(this.msgType, that.msgType)
            && Objects.equals(this.fieldName, that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msgType, this.fieldName);
    }
}
