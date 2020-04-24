package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class BadUriPattern extends IncomingHttpSgnlError {
    private final String uriPattern;

    public BadUriPattern(String uriPattern) {
        super(String.format("URI pattern '%s' is not valid", uriPattern));
        this.uriPattern = uriPattern;
    }

    public BadUriPattern(String uriPattern, Throwable cause) {
        super(String.format("URI pattern '%s' is not valid", uriPattern), cause);
        this.uriPattern = uriPattern;
    }

    @Override
    public HttpStatus rejectStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
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
