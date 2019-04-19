package lorikeet.web.impl;

import lorikeet.web.HttpHeaders;
import lorikeet.web.HttpMethod;
import lorikeet.web.IncomingRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.URI;
import java.util.Objects;

public class StandardIncomingRequest implements IncomingRequest {

    private final URI uri;
    private final HttpMethod method;
    private final HttpHeaders headers;

    public StandardIncomingRequest(URI uri, HttpMethod method, HttpHeaders headers) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
    }

    @Override
    public URI getURI() {
        return null;
    }

    @Override
    public HttpMethod getMethod() {
        return this.method;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        StandardIncomingRequest that = (StandardIncomingRequest) o;

        return Objects.equals(this.getURI(), that.getURI())
            && Objects.equals(this.getMethod(), that.getMethod())
            && Objects.equals(this.getHeaders(), that.getHeaders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getURI(), this.getMethod(), this.getHeaders());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("uri", this.getURI())
            .append("method", this.getMethod())
            .append("headers", this.getHeaders())
            .toString();
    }
}