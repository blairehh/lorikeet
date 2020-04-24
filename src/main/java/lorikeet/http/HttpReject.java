package lorikeet.http;

import lorikeet.core.BasicErrResult;
import lorikeet.core.Seq;
import lorikeet.http.error.IncomingHttpSgnlError;

import java.util.Objects;

public class HttpReject extends BasicErrResult<HttpReplier, IncomingHttpSgnlError> implements HttpDirective {

    private final Seq<IncomingHttpSgnlError> errors;

    public HttpReject(Seq<IncomingHttpSgnlError> errors) {
        this.errors = errors;
    }

    @Override
    public IncomingHttpSgnlError exception() {
        return this.errors.first().orElseThrow();
    }

    @Override
    public Seq<? extends IncomingHttpSgnlError> errors() {
        return this.errors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpReject that = (HttpReject) o;

        return Objects.equals(this.errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.errors);
    }
}
