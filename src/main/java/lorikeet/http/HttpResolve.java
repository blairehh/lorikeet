package lorikeet.http;


import lorikeet.core.BasicOkResult;
import lorikeet.http.error.IncomingHttpSgnlError;

import java.util.Objects;

public class HttpResolve extends BasicOkResult<HttpReplier, IncomingHttpSgnlError> implements HttpDirective {
    private final HttpReplier replier;

    public HttpResolve(HttpReplier replier) {
        this.replier = replier;
    }

    @Override
    public HttpReplier value() {
        return this.replier;
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

        return Objects.equals(this.replier, that.replier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.replier);
    }
}
