package lorikeet.http;

import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.http.error.BadPathVariableName;
import lorikeet.http.error.BadPathVariableValue;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.error.PathVarNotFound;
import lorikeet.http.internal.HttpMsgPath;
import lorikeet.http.internal.IncomingHttpSgnlStrop;

import java.util.function.Function;

public abstract class NumberPathVar<T extends Number> implements IncomingHttpSgnlStrop<T> {
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
    public FallibleResult<T, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
        if (this.name == null || this.name.isBlank()) {
            return new ErrResult<>(new BadPathVariableName(this.name));
        }
        final String value = this.path.pathVariables()
            .pick(this.name)
            .orElse(null);

        if (value == null) {
            return new ErrResult<>(new PathVarNotFound(this.name));
        }

        try {
            return new OkResult<>(this.parser.apply(value));
        } catch (NumberFormatException e) {
            return new ErrResult<>(new BadPathVariableValue(value, this.valueType));
        }
    }
}
