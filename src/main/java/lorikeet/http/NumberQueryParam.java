package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.error.BadQueryParameterName;
import lorikeet.http.error.BadQueryParameterValue;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.error.QueryParameterNotFound;
import lorikeet.http.internal.IncomingHttpSgnlStrop;

import java.util.Objects;
import java.util.function.Function;

public abstract class NumberQueryParam<T extends Number> implements IncomingHttpSgnlStrop<T> {
    private final String queryParamName;
    private final Function<String, T> parser;
    private final Class<T> valueType;

    public NumberQueryParam(String queryParamName, Function<String, T> parser, Class<T> valueType) {
        this.queryParamName = queryParamName;
        this.parser = parser;
        this.valueType = valueType;
    }

    @Override
    public FallibleResult<T, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
        if (this.queryParamName == null || this.queryParamName.isBlank()) {
            return new ErrResult<>(new BadQueryParameterName(this.queryParamName));
        }
        final Dict<String, Seq<String>> queryParams = request.queryParameters();
        final Seq<String> values = queryParams.pick(this.queryParamName)
            .orElse(new SeqOf<>());
        if (values.isEmpty()) {
            return new ErrResult<>(new QueryParameterNotFound(this.queryParamName));
        }

        try {
            return new OkResult<>(this.parser.apply(values.pick(0).orElseThrow()));
        } catch (NumberFormatException e) {
            return new ErrResult<>(new BadQueryParameterValue(values.pick(0).orElseThrow(), this.valueType));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        NumberQueryParam<?> that = (NumberQueryParam<?>) o;

        return Objects.equals(this.queryParamName, that.queryParamName)
            && Objects.equals(this.parser, that.parser)
            && Objects.equals(this.valueType, that.valueType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.queryParamName, this.parser, this.valueType);
    }
}
