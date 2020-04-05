package lorikeet.http;

import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.core.Seq;
import lorikeet.http.error.HeaderNotFound;
import lorikeet.lobe.IncomingHttpMsg;

import java.util.Objects;

public class StringHeader implements IncludableFallible<String> {
    private final IncomingHttpMsg msg;
    private final String headerName;
    private final String defaultValue;

    public StringHeader(IncomingHttpMsg msg, String headerName, String defaultValue) {
        this.msg = msg;
        this.headerName = headerName;
        this.defaultValue = defaultValue;
    }

    public StringHeader(IncomingHttpMsg msg, String headerName) {
        this.msg = msg;
        this.headerName = headerName;
        this.defaultValue = null;
    }

    @Override
    public Fallible<String> include() {
        final var opt = this.msg.headers()
            .pick(this.headerName)
            .flatMap(Seq::first);

        if (this.defaultValue != null) {
            return new Ok<>(opt.orElse(this.defaultValue));
        }

        return new Err<>(new HeaderNotFound(this.headerName));
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
