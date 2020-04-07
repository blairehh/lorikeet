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
import lorikeet.lobe.IncomingHttpMsg;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class NumberHeader<T extends Number> implements IncludableFallible<T> {
    private final IncomingHttpMsg msg;
    private final String headerName;
    private final Function<String, T> parser;
    private final T defaultValue;

    public NumberHeader(IncomingHttpMsg msg, String headerName, Function<String, T> parser, T defaultValue) {
        this.msg = msg;
        this.headerName = headerName;
        this.parser = parser;
        this.defaultValue = defaultValue;
    }

    public NumberHeader(IncomingHttpMsg msg, String headerName, Function<String, T> parser) {
        this.msg = msg;
        this.headerName = headerName;
        this.parser = parser;
        this.defaultValue = null;
    }

    @Override
    public Fallible<T> include() {
        if (this.headerName.isBlank()) {
            return new Bug<>(new BadHeaderName(this.headerName));
        }

        final Optional<String> opt = this.msg.headers()
            .pick(this.headerName)
            .flatMap(Seq::first);

        if (opt.isEmpty() && this.defaultValue == null) {
            return new Err<>(new HeaderNotFound(this.headerName));
        }

        if (opt.isEmpty()) {
            return new Ok<>(this.defaultValue);
        }

        final String value = opt.orElseThrow();

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

        return Objects.equals(this.msg, that.msg)
            && Objects.equals(this.headerName, that.headerName)
            && Objects.equals(this.parser, that.parser)
            && Objects.equals(this.defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msg, this.headerName, this.parser, this.defaultValue);
    }
}
