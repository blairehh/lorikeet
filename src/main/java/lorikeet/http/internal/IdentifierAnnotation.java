package lorikeet.http.internal;

import lorikeet.resource.HttpMethod;

import java.util.Objects;

public class IdentifierAnnotation {
    private final HttpMethod method;
    private final String uriPattern;

    public IdentifierAnnotation(HttpMethod method, String uriPattern) {
        this.method = method;
        this.uriPattern = uriPattern;
    }

    public HttpMethod method() {
        return this.method;
    }

    public String uriPattern() {
        return this.uriPattern;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        IdentifierAnnotation that = (IdentifierAnnotation) o;

        return Objects.equals(this.method(), that.method())
            && Objects.equals(this.uriPattern(), that.uriPattern());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.method(), this.uriPattern());
    }
}
