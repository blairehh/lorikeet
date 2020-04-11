package lorikeet.http;

import java.util.Objects;

public class HttpResolve implements HttpDirective {
    private final Runnable runnable;

    public HttpResolve(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public boolean reject() {
        return false;
    }

    @Override
    public void perform() {
        this.runnable.run();
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

        return Objects.equals(this.runnable, that.runnable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.runnable);
    }
}
