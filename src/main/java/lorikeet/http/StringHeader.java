package lorikeet.http;

import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.http.error.BadHeaderName;
import lorikeet.http.error.HeaderNotFound;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.internal.IncomingHttpSgnlStrop;

import java.util.Objects;

public class StringHeader implements IncomingHttpSgnlStrop<String> {
    private final String headerName;
    private final String defaultValue;

    public StringHeader(String headerName, String defaultValue) {
        this.headerName = headerName;
        this.defaultValue = defaultValue;
    }

    public StringHeader(String headerName) {
        this.headerName = headerName;
        this.defaultValue = null;
    }

    public StringHeader(HeaderField header, String defaultValue) {
        this.headerName = header.key();
        this.defaultValue = defaultValue;
    }

    public StringHeader(HeaderField header) {
        this.headerName = header.key();
        this.defaultValue = null;
    }

    @Override
    public FallibleResult<String, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
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
       return new OkResult<>(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        StringHeader that = (StringHeader) o;

        return Objects.equals(this.headerName, that.headerName)
            && Objects.equals(this.defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headerName, this.defaultValue);
    }
}
