package lorikeet.ecosphere.testing.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
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

    @Override
    public String toString() {
        return String.format("'%s'", this.value);
    }
}
