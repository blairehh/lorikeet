package lorikeet.http.error;

import lorikeet.http.HttpStatus;

import java.util.Objects;

public class HeaderNotFound extends IncomingHttpSgnlError {
    private final String headerName;

    public HeaderNotFound(String headerName) {
        super(String.format("Header '%s' was not found", headerName));
        this.headerName = headerName;
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

        HeaderNotFound that = (HeaderNotFound) o;

        return Objects.equals(this.headerName, that.headerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headerName);
    }
}
