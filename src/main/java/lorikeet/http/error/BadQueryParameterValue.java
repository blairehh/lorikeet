package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class BadQueryParameterValue extends IncomingHttpSgnlError {
    private final String value;
    private final Class<?> valueType;

    public BadQueryParameterValue(String value, Class<?> valueType) {
        super(String.format("Query parameter value '%s' is not valid for type '%s'", value, valueType));
        this.value = value;
        this.valueType = valueType;
    }

    @Override
    public HttpStatus rejectStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BadQueryParameterValue that = (BadQueryParameterValue) o;

        return Objects.equals(this.value, that.value)
            && Objects.equals(this.valueType, that.valueType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value, this.valueType);
    }
}
