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

public class BoolHeader implements IncludableFallible<Boolean> {
    private final IncomingHttpMsg msg;
    private final String headerName;
    private final Boolean defaultValue;

    public BoolHeader(IncomingHttpMsg msg, String headerName, Boolean defaultValue) {
        this.msg = msg;
        this.headerName = headerName;
        this.defaultValue = defaultValue;
    }

    public BoolHeader(IncomingHttpMsg msg, String headerName) {
        this.msg = msg;
        this.headerName = headerName;
        this.defaultValue = null;
    }

    @Override
    public Fallible<Boolean> include() {
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

        return Objects.equals(this.msg, that.msg)
            && Objects.equals(this.headerName, that.headerName)
            && Objects.equals(this.defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msg, this.headerName, this.defaultValue);
    }
}
