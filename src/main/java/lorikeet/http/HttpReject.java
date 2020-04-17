package lorikeet.http;

import lorikeet.core.AnErr;
import lorikeet.core.Seq;

import java.util.Objects;

public class HttpReject implements AnErr<HttpReplier>, HttpDirective {

    private final Seq<Exception> errors;

    public HttpReject(Seq<Exception> errors) {
        this.errors = errors;
    }

    @Override
    public Exception exception() {
        return this.errors.first().orElseThrow();
    }

    @Override
    public Seq<? extends Exception> errors() {
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
