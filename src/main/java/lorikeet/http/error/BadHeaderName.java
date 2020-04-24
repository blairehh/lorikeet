package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class BadHeaderName extends IncomingHttpSgnlError {
    private final String headerName;

    public BadHeaderName(String name) {
        super(String.format("Bad header name '%s'", name));
        this.headerName = name;
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

        BadHeaderName that = (BadHeaderName) o;

        return Objects.equals(this.headerName, that.headerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headerName);
    }
}
