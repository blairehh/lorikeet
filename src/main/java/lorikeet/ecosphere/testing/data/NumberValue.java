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
