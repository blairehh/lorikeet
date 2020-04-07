package lorikeet.http.internal;

import lorikeet.core.Dict;

import java.net.URI;
import java.util.Objects;

public class HttpMsgPath {
    private final URI uri;
    private final Dict<String, String> pathVariables;

    public HttpMsgPath(URI uri, Dict<String, String> pathVariables) {
        this.uri = uri;
        this.pathVariables = pathVariables;
    }

    public URI uri() {
        return this.uri;
    }

    public Dict<String, String> pathVariables() {
        return this.pathVariables;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HttpMsgPath that = (HttpMsgPath) o;

        return Objects.equals(this.uri(), that.uri())
            && Objects.equals(this.pathVariables(), that.pathVariables());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uri, this.pathVariables);
    }
}
