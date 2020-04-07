package lorikeet.http;

import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.http.error.BadPathVariableName;
import lorikeet.http.error.BadPathVariableValue;
import lorikeet.http.error.PathVarNotFound;
import lorikeet.http.internal.HttpMsgPath;

import java.util.function.Function;

public abstract class NumberPathVar<T extends Number> implements IncludableFallible<T> {
    private final HttpMsgPath path;
    private final String name;
    private final Function<String, T> parser;
    private final Class<T> valueType;

    public NumberPathVar(HttpMsgPath path, String name, Function<String, T> parser, Class<T> valueType) {
        this.path = path;
        this.name = name;
        this.parser = parser;
        this.valueType = valueType;
    }

    @Override
    public Fallible<T> include() {
        if (this.name == null || this.name.isBlank()) {
            return new Err<>(new BadPathVariableName(this.name));
        }
        final String value = this.path.pathVariables()
            .pick(this.name)
            .orElse(null);

        if (value == null) {
            return new Err<>(new PathVarNotFound(this.name));
        }

        try {
            return new Ok<>(this.parser.apply(value));
        } catch (NumberFormatException e) {
            return new Err<>(new BadPathVariableValue(value, this.valueType));
        }
    }
}
