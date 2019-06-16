package lorikeet.ecosphere.testing.data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class BoolValue implements Value {
    private final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }

        if (!(other instanceof BoolValue)) {
            return Equality.NOT_EQUAL;
        }

        final boolean otherValue = ((BoolValue)other).getValue();
        if (value == otherValue) {
            return Equality.EQUAL;
        }
        return Equality.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BoolValue that = (BoolValue) o;

        return Objects.equals(this.getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValue());
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
