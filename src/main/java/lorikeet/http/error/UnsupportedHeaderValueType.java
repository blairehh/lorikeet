package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class UnsupportedHeaderValueType extends IncomingHttpSgnlError {
    private final Class<?> valueType;
    public UnsupportedHeaderValueType(Class<?> valueType) {
        super(String.format("Type '%s' is not supported as a heaver value", valueType));
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

        UnsupportedHeaderValueType that = (UnsupportedHeaderValueType) o;

        return Objects.equals(this.valueType, that.valueType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.valueType);
    }
}
