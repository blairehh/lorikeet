package lorikeet.ecosphere.testing.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class NumberValue implements Value {
    private final Number value;

    public NumberValue(Number value) {
        this.value = value.doubleValue();
    }

    public Number getValue() {
        return this.value;
    }

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }

        if (!(other instanceof NumberValue)) {
            return Equality.NOT_EQUAL;
        }

        final NumberValue otherValue = (NumberValue) other;
        if (this.equals(otherValue)) {
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

        NumberValue that = (NumberValue) o;

        return Objects.equals(this.getValue().doubleValue(), that.getValue().doubleValue());
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
