package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.error.BadQueryParameterName;
import lorikeet.http.error.BadQueryParameterValue;
import lorikeet.http.error.QueryParameterNotFound;
import lorikeet.lobe.IncomingHttpMsg;
import java.util.function.Function;

public abstract class NumberQueryParam<T extends Number> implements IncludableFallible<T> {
    private final IncomingHttpMsg msg;
    private final String queryParamName;
    private final Function<String, T> parser;
    private final Class<T> valueType;

    public NumberQueryParam(IncomingHttpMsg msg, String queryParamName, Function<String, T> parser, Class<T> valueType) {
        this.msg = msg;
        this.queryParamName = queryParamName;
        this.parser = parser;
        this.valueType = valueType;
    }

    @Override
    public Fallible<T> include() {
        if (this.queryParamName == null || this.queryParamName.isBlank()) {
            return new Err<>(new BadQueryParameterName(this.queryParamName));
        }
        final Dict<String, Seq<String>> queryParams = this.msg.queryParameters();
        final Seq<String> values = queryParams.pick(this.queryParamName)
            .orElse(new SeqOf<>());
        if (values.isEmpty()) {
            return new Err<>(new QueryParameterNotFound(this.queryParamName));
        }

        try {
            return new Ok<>(this.parser.apply(values.pick(0).orElseThrow()));
        } catch (NumberFormatException e) {
            return new Err<>(new BadQueryParameterValue(values.pick(0).orElseThrow(), this.valueType));
        }
    }

}
