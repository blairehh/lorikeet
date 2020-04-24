package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class UnsupportedPathValueType extends IncomingHttpSgnlError {
    private final Class<?> valueType;

    public UnsupportedPathValueType(Class<?> valueType) {
        super(String.format("Type '%s' is not a valid path type", valueType));
        this.valueType = valueType;
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

        UnsupportedPathValueType that = (UnsupportedPathValueType) o;

        return Objects.equals(this.valueType, that.valueType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.valueType);
    }
}
