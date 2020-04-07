package lorikeet.http.error;

import java.util.Objects;

public class BadPathVariableName extends RuntimeException {
    private final String name;

    public BadPathVariableName(String name) {
        super(String.format("Path variable name '%s' is not valid", name));
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BadPathVariableName that = (BadPathVariableName) o;

        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
