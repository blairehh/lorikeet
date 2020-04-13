package lorikeet.http;

import lorikeet.core.Bug;
import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.core.Seq;
import lorikeet.http.error.BadHeaderName;
import lorikeet.http.error.BadHeaderValue;
import lorikeet.http.error.HeaderNotFound;

import java.util.Objects;
import java.util.Optional;

public class BoolHeader implements IncludableFallible<Boolean> {
    private final IncomingHttpSgnl request;
    private final String headerName;
    private final Boolean defaultValue;

    public BoolHeader(IncomingHttpSgnl request, String headerName, Boolean defaultValue) {
        this.request = request;
        this.headerName = headerName;
        this.defaultValue = defaultValue;
    }

    public BoolHeader(IncomingHttpSgnl request, String headerName) {
        this.request = request;
        this.headerName = headerName;
        this.defaultValue = null;
    }

    public BoolHeader(IncomingHttpSgnl request, HeaderField header, Boolean defaultValue) {
        this.request = request;
        this.headerName = header.key();
        this.defaultValue = defaultValue;
    }

    public BoolHeader(IncomingHttpSgnl request, HeaderField header) {
        this.request = request;
        this.headerName = header.key();
        this.defaultValue = null;
    }

    @Override
    public Fallible<Boolean> include() {
        if (this.headerName.isBlank()) {
            return new Bug<>(new BadHeaderName(this.headerName));
        }

        final String value = this.request.headers().getAny(this.headerName);

        if (value.isBlank() && this.defaultValue == null) {
            return new Err<>(new HeaderNotFound(this.headerName));
        }

        if (value.isBlank()) {
            return new Ok<>(this.defaultValue);
        }

        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return new Ok<>("true".equalsIgnoreCase(value));
        }
        return new Err<>(new BadHeaderValue(this.headerName, "(true|false)"));
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

        return Objects.equals(this.request, that.request)
            && Objects.equals(this.headerName, that.headerName)
            && Objects.equals(this.defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.request, this.headerName, this.defaultValue);
    }
}
