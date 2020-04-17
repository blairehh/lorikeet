package lorikeet.http;

import java.util.Objects;
import java.util.function.Supplier;

public class HttpResolve implements HttpDirective {
    private final Supplier<HttpReply> action;

    public HttpResolve(Supplier<HttpReply> action) {
        this.action = action;
    }

    @Override
    public boolean reject() {
        return false;
    }

    @Override
    public HttpReply perform() {
        return this.action.get();
    }

    @Override
    public boolean wrongMethod() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpResolve that = (HttpResolve) o;

        return Objects.equals(this.action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.action);
    }
}
