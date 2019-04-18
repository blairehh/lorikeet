package lorikeet.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public final class Mapping {
    private final HttpMethod method;
    private final String path;
    private final WebEndpoint endpoint;

    public Mapping(HttpMethod method, String path, WebEndpoint endpoint) {
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

    public WebEndpoint getEndpoint() {
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

        Mapping mapping = (Mapping) o;

        return Objects.equals(this.getMethod(), mapping.getMethod())
            && Objects.equals(this.getPath(), mapping.getPath())
            && Objects.equals(this.getEndpoint(), mapping.getEndpoint());
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
