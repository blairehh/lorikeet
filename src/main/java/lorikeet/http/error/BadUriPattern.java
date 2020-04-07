package lorikeet.http.error;

import java.util.Objects;

public class BadUriPattern extends RuntimeException {
    private final String uriPattern;

    public BadUriPattern(String uriPattern, Throwable cause) {
        super(String.format("URI pattern '%s' is not valid", uriPattern), cause);
        this.uriPattern = uriPattern;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BadUriPattern that = (BadUriPattern) o;

        return Objects.equals(this.uriPattern, that.uriPattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uriPattern);
    }
}
