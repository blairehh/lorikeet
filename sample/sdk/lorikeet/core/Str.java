package lorikeet.core;

import java.util.Objects;

public class Str {
    private final String value;

    public Str(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Str that = (Str)o;

        return (
            Objects.equals(this.value, that.value)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
