package lorikeet.http;

import lorikeet.core.AnErr;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;

import java.util.Objects;

public class HttpWriteErr implements AnErr<Boolean>, HttpWriteResult {
    private final Exception exception;
    private final int statusCode;

    public HttpWriteErr(Exception exception, int statusCode) {
        this.exception = exception;
        this.statusCode = statusCode;
    }

    @Override
    public Seq<? extends Exception> errors() {
        return new SeqOf<>(this.exception);
    }

    @Override
    public Exception exception() {
        return this.exception;
    }

    @Override
    public int statusCode() {
        return this.statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpWriteErr that = (HttpWriteErr) o;

        return Objects.equals(this.statusCode, that.statusCode)
            && Objects.equals(this.exception, that.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.exception, this.statusCode);
    }
}
