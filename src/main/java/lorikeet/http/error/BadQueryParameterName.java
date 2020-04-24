package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class BadQueryParameterName extends IncomingHttpSgnlError {
    private final String queryParameterName;

    public BadQueryParameterName(String queryParameterName) {
        super(String.format("Query parameter name '%s' is not valid", queryParameterName));
        this.queryParameterName = queryParameterName;
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

        BadQueryParameterName that = (BadQueryParameterName) o;

        return Objects.equals(this.queryParameterName, that.queryParameterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.queryParameterName);
    }
}
