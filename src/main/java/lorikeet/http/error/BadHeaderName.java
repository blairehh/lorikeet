package lorikeet.http.error;

import java.util.Objects;

public class BadHeaderName extends RuntimeException {
    private final String headerName;

    public BadHeaderName(String name) {
        super(String.format("Bad header name '%s'", name));
        this.headerName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BadHeaderName that = (BadHeaderName) o;

        return Objects.equals(this.headerName, that.headerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headerName);
    }
}
