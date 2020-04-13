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
import java.util.function.Function;

public abstract class NumberHeader<T extends Number> implements IncludableFallible<T> {
    private final IncomingHttpSgnl request;
    private final String headerName;
    private final Function<String, T> parser;
    private final T defaultValue;

    public NumberHeader(IncomingHttpSgnl request, String headerName, Function<String, T> parser, T defaultValue) {
        this.request = request;
        this.headerName = headerName;
        this.parser = parser;
        this.defaultValue = defaultValue;
    }

    public NumberHeader(IncomingHttpSgnl request, HeaderField header, Function<String, T> parser) {
        this.request = request;
        this.headerName = header.key();
        this.parser = parser;
        this.defaultValue = null;
    }

    public NumberHeader(IncomingHttpSgnl request, HeaderField header, Function<String, T> parser, T defaultValue) {
        this.request = request;
        this.headerName = header.key();
        this.parser = parser;
        this.defaultValue = defaultValue;
    }

    public NumberHeader(IncomingHttpSgnl request, String headerName, Function<String, T> parser) {
        this.request = request;
        this.headerName = headerName;
        this.parser = parser;
        this.defaultValue = null;
    }

    @Override
    public Fallible<T> include() {
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

        try {
            return new Ok<>(this.parser.apply(value));
        } catch (NumberFormatException e) {
            return new Err<>(new BadHeaderValue(this.headerName, "\\d+"));
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

        return Objects.equals(this.request, that.request)
            && Objects.equals(this.headerName, that.headerName)
            && Objects.equals(this.parser, that.parser)
            && Objects.equals(this.defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.request, this.headerName, this.parser, this.defaultValue);
    }
}
