package lorikeet.http.error;

import java.util.Objects;

public class StatusCodeFiledCanNotBeNull extends RuntimeException {
    private final Class<?> msgType;
    private final String fieldName;

    public StatusCodeFiledCanNotBeNull(Class<?> msgType, String fieldName) {
        super(String.format("Status Code field '%s' on msg '%s' can not be null", msgType.getCanonicalName(), fieldName));
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

        StatusCodeFiledCanNotBeNull that = (StatusCodeFiledCanNotBeNull) o;

        return Objects.equals(this.msgType, that.msgType)
            && Objects.equals(this.fieldName, that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msgType, this.fieldName);
    }
}
