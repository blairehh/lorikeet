package lorikeet.http;

import java.util.Objects;

public class HttpReject implements HttpDirective {
    private final boolean wrongMethod;

    public HttpReject(boolean wrongMethod) {
        this.wrongMethod = wrongMethod;
    }

    @Override
    public boolean reject() {
        return true;
    }

    @Override
    public HttpReply perform() {
        return new HttpNoOp();
    }

    @Override
    public boolean wrongMethod() {
        return this.wrongMethod;
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

        return Objects.equals(this.wrongMethod(), that.wrongMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.wrongMethod());
    }
}
