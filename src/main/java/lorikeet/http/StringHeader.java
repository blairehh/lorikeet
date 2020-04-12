package lorikeet.http;

import lorikeet.core.Bug;
import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.core.Seq;
import lorikeet.http.error.BadHeaderName;
import lorikeet.http.error.HeaderNotFound;

import java.util.Objects;

public class StringHeader implements IncludableFallible<String> {
    private final IncomingHttpSgnl msg;
    private final String headerName;
    private final String defaultValue;

    public StringHeader(IncomingHttpSgnl msg, String headerName, String defaultValue) {
        this.msg = msg;
        this.headerName = headerName;
        this.defaultValue = defaultValue;
    }

    public StringHeader(IncomingHttpSgnl msg, String headerName) {
        this.msg = msg;
        this.headerName = headerName;
        this.defaultValue = null;
    }

    @Override
    public Fallible<String> include() {
        if (this.headerName.isBlank()) {
            return new Bug<>(new BadHeaderName(this.headerName));
        }

        final var opt = this.msg.headers()
            .pick(this.headerName)
            .flatMap(Seq::first);

        if (opt.isEmpty() && this.defaultValue == null) {
            return new Err<>(new HeaderNotFound(this.headerName));
        }

        if (opt.isEmpty()) {
            return new Ok<>(this.defaultValue);
        }
       return new Ok<>(opt.orElseThrow());
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

        return Objects.equals(this.msg, that.msg)
            && Objects.equals(this.headerName, that.headerName)
            && Objects.equals(this.defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.msg, this.headerName, this.defaultValue);
    }
}
