package lorikeet.http;

import lorikeet.core.AnOk;

import java.util.Objects;

public class HttpWriteOk implements AnOk<Boolean>, HttpWriteResult {
    private final int statusCode;

    public HttpWriteOk(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public Boolean value() {
        return true;
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

        HttpWriteOk that = (HttpWriteOk) o;

        return Objects.equals(this.statusCode(), that.statusCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.statusCode());
    }
}
