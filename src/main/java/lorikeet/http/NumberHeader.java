package lorikeet.http;

import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.http.error.BadHeaderName;
import lorikeet.http.error.BadHeaderValue;
import lorikeet.http.error.HeaderNotFound;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.internal.IncomingHttpSgnlStrop;

import java.util.Objects;
import java.util.function.Function;

public abstract class NumberHeader<T extends Number> implements IncomingHttpSgnlStrop<T> {
    private final String headerName;
    private final Function<String, T> parser;
    private final T defaultValue;

    public NumberHeader(String headerName, Function<String, T> parser, T defaultValue) {
        this.headerName = headerName;
        this.parser = parser;
        this.defaultValue = defaultValue;
    }

    public NumberHeader(HeaderField header, Function<String, T> parser) {
        this.headerName = header.key();
        this.parser = parser;
        this.defaultValue = null;
    }

    public NumberHeader(HeaderField header, Function<String, T> parser, T defaultValue) {
        this.headerName = header.key();
        this.parser = parser;
        this.defaultValue = defaultValue;
    }

    public NumberHeader(String headerName, Function<String, T> parser) {
        this.headerName = headerName;
        this.parser = parser;
        this.defaultValue = null;
    }

    @Override
    public FallibleResult<T, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
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

        try {
            return new OkResult<>(this.parser.apply(value));
        } catch (NumberFormatException e) {
            return new ErrResult<>(new BadHeaderValue(this.headerName, "\\d+"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        NumberHeader<?> that = (NumberHeader<?>) o;

        return Objects.equals(this.headerName, that.headerName)
            && Objects.equals(this.parser, that.parser)
            && Objects.equals(this.defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.headerName, this.parser, this.defaultValue);
    }
}
