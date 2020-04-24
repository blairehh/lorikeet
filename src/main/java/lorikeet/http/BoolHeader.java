package lorikeet.http;

import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.http.error.BadHeaderName;
import lorikeet.http.error.BadHeaderValue;
import lorikeet.http.error.HeaderNotFound;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.internal.IncomingHttpSgnlStreamInclude;

import java.util.Objects;

public class BoolHeader implements IncomingHttpSgnlStreamInclude<Boolean> {
    private final String headerName;
    private final Boolean defaultValue;

    public BoolHeader(String headerName, Boolean defaultValue) {
        this.headerName = headerName;
        this.defaultValue = defaultValue;
    }

    public BoolHeader(String headerName) {
        this.headerName = headerName;
        this.defaultValue = null;
    }

    public BoolHeader(HeaderField header, Boolean defaultValue) {
        this.headerName = header.key();
        this.defaultValue = defaultValue;
    }

    public BoolHeader(HeaderField header) {
        this.headerName = header.key();
        this.defaultValue = null;
    }

    @Override
    public FallibleResult<Boolean, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
        if (this.headerName.isBlank()) {
            return new ErrResult<>(new BadHeaderName(this.headerName));
        }

        final String value = request.headers().getAny(this.headerName);

        if (value.isBlank() && this.defaultValue == null) {
            return new ErrResult<>(new HeaderNotFound(this.headerName));
        }

        if (value.isBlank()) {
            return new OkResult<>(this.defaultValue);
        }

        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return new OkResult<>("true".equalsIgnoreCase(value));
        }
        return new ErrResult<>(new BadHeaderValue(this.headerName, "(true|false)"));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BoolHeader that = (BoolHeader) o;

        return Objects.equals(this.headerName, that.headerName)
            && Objects.equals(this.defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headerName, this.defaultValue);
    }
}
