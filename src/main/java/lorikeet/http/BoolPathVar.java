package lorikeet.http;

import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.http.error.BadPathVariableName;
import lorikeet.http.error.BadPathVariableValue;
import lorikeet.http.error.PathVarNotFound;
import lorikeet.http.internal.HttpMsgPath;

public class BoolPathVar implements IncludableFallible<Boolean> {
    private final HttpMsgPath path;
    private final String pathVarName;

    public BoolPathVar(HttpMsgPath path, String pathVarName) {
        this.path = path;
        this.pathVarName = pathVarName;
    }

    @Override
    public Fallible<Boolean> include() {
        if (this.pathVarName == null || this.pathVarName.isBlank()) {
            return new Err<>(new BadPathVariableName(this.pathVarName));
        }

        final String value = path.pathVariables()
            .pick(this.pathVarName)
            .orElse(null);

        if (value == null) {
            return new Err<>(new PathVarNotFound(this.pathVarName));
        }

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return new Ok<>(value.equalsIgnoreCase("true"));
        }

        return new Err<>(new BadPathVariableValue(value, Boolean.class));
    }
}
