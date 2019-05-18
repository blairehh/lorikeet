package lorikeet.ecosphere.transcript;

import java.util.Objects;

public class NumberValue implements Value {
    private final Number value;

    public NumberValue(Number value) {
        this.value = value;
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

        StringValue that = (StringValue) o;

        return Objects.equals(this.getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValue());
    }
}
