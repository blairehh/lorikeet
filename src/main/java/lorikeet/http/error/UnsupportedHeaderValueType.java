package lorikeet.http.error;

import java.util.Objects;

public class UnsupportedHeaderValueType extends RuntimeException {
    private final Class<?> valueType;
    public UnsupportedHeaderValueType(Class<?> valueType) {
        super(String.format("Type '%s' is not supported as a heaver value", valueType));
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

        UnsupportedHeaderValueType that = (UnsupportedHeaderValueType) o;

        return Objects.equals(this.valueType, that.valueType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.valueType);
    }
}
