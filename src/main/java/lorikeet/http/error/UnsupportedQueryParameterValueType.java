package lorikeet.http.error;

import java.util.Objects;

public class UnsupportedQueryParameterValueType extends RuntimeException {
    private final Class<?> valueType;

    public UnsupportedQueryParameterValueType(Class<?> valueType) {
        super(String.format("Type '%s' is not a valid query parameter value type", valueType.getCanonicalName()));
        this.valueType = valueType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        UnsupportedQueryParameterValueType that = (UnsupportedQueryParameterValueType) o;

        return Objects.equals(this.valueType, that.valueType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.valueType);
    }
}
