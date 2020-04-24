package lorikeet.http;

import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.http.error.BadPathVariableName;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.error.PathVarNotFound;
import lorikeet.http.internal.HttpMsgPath;
import lorikeet.http.internal.IncomingHttpSgnlStreamInclude;

import java.util.Objects;

public class StringPathVar implements IncomingHttpSgnlStreamInclude<String> {
    private final HttpMsgPath path;
    private final String name;

    public StringPathVar(HttpMsgPath path, String name) {
        this.path = path;
        this.name = name;
    }

    @Override
    public FallibleResult<String, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
        if (this.name == null || this.name.isBlank()) {
            return new ErrResult<>(new BadPathVariableName(this.name));
        }

        final String value = path.pathVariables()
            .pick(this.name)
            .orElse(null);

        if (value == null) {
            return new ErrResult<>(new PathVarNotFound(this.name));
        }

        return new OkResult<>(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        StringPathVar that = (StringPathVar) o;

        return Objects.equals(this.path, that.path)
            && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.path, this.name);
    }
}
