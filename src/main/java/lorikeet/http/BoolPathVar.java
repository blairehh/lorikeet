package lorikeet.http;

import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.http.error.BadPathVariableName;
import lorikeet.http.error.BadPathVariableValue;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.error.PathVarNotFound;
import lorikeet.http.internal.HttpMsgPath;
import lorikeet.http.internal.IncomingHttpSgnlStreamInclude;

import java.util.Objects;

public class BoolPathVar implements IncomingHttpSgnlStreamInclude<Boolean> {
    private final HttpMsgPath path;
    private final String pathVarName;

    public BoolPathVar(HttpMsgPath path, String pathVarName) {
        this.path = path;
        this.pathVarName = pathVarName;
    }

    @Override
    public FallibleResult<Boolean, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
        if (this.pathVarName == null || this.pathVarName.isBlank()) {
            return new ErrResult<>(new BadPathVariableName(this.pathVarName));
        }

        final String value = path.pathVariables()
            .pick(this.pathVarName)
            .orElse(null);

        if (value == null) {
            return new ErrResult<>(new PathVarNotFound(this.pathVarName));
        }

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return new OkResult<>(value.equalsIgnoreCase("true"));
        }

        return new ErrResult<>(new BadPathVariableValue(value, Boolean.class));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BoolPathVar that = (BoolPathVar) o;

        return Objects.equals(this.path, that.path)
            && Objects.equals(this.pathVarName, that.pathVarName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.path, this.pathVarName);
    }
}
