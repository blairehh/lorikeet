package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class UriPatternDoesNotMatchUri extends IncomingHttpSgnlError {
    private final String uri;
    private final String uriPattern;

    public UriPatternDoesNotMatchUri(String uriPattern, String uri) {
        super(String.format("URI pattern '%s' does not match URI '%s'", uriPattern, uri));
        this.uri = uri;
        this.uriPattern = uriPattern;
    }

    @Override
    public HttpStatus rejectStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        UriPatternDoesNotMatchUri that = (UriPatternDoesNotMatchUri) o;

        return Objects.equals(this.uri, that.uri)
            && Objects.equals(this.uriPattern, that.uriPattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uri, this.uriPattern);
    }
}
