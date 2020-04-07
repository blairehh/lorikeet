package lorikeet.http;

import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.http.error.BadPathVariableName;
import lorikeet.http.error.PathVarNotFound;
import lorikeet.http.internal.HttpMsgPath;

public class StringPathVar implements IncludableFallible<String> {
    private final HttpMsgPath path;
    private final String name;

    public StringPathVar(HttpMsgPath path, String name) {
        this.path = path;
        this.name = name;
    }

    @Override
    public Fallible<String> include() {
        if (this.name == null || this.name.isBlank()) {
            return new Err<>(new BadPathVariableName(this.name));
        }

        final String value = path.pathVariables()
            .pick(this.name)
            .orElse(null);

        if (value == null) {
            return new Err<>(new PathVarNotFound(this.name));
        }

        return new Ok<>(value);
    }
}
