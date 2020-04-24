package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class QueryParameterNotFound extends IncomingHttpSgnlError {
    private final String queryParameterName;

    public QueryParameterNotFound(String queryParameterName) {
        super(String.format("Query parameter '%s' was not found", queryParameterName));
        this.queryParameterName = queryParameterName;
    }

    @Override
    public HttpStatus rejectStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        QueryParameterNotFound that = (QueryParameterNotFound) o;

        return Objects.equals(this.queryParameterName, that.queryParameterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.queryParameterName);
    }
}
