package lorikeet.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public final class WebEndpoint {
    private final HttpMethod method;
    private final String path;
    private final IncomingRequestHandler endpoint;

    public WebEndpoint(HttpMethod method, String path, IncomingRequestHandler endpoint) {
        this.method = method;
        this.path = path;
        this.endpoint = endpoint;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public IncomingRequestHandler getEndpoint() {
        return this.endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        WebEndpoint webEndpoint = (WebEndpoint) o;

        return Objects.equals(this.getMethod(), webEndpoint.getMethod())
            && Objects.equals(this.getPath(), webEndpoint.getPath())
            && Objects.equals(this.getEndpoint(), webEndpoint.getEndpoint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getMethod(), this.getPath(), this.getEndpoint());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("method", this.getMethod())
            .append("path", this.getPath())
            .toString();
    }
}
