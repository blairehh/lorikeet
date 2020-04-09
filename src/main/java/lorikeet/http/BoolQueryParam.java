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

public class BoolQueryParam implements IncludableFallible<Boolean> {
    private final IncomingHttpMsg msg;
    private final String queryParameterName;

    public BoolQueryParam(IncomingHttpMsg msg, String queryParameterName) {
        this.msg = msg;
        this.queryParameterName = queryParameterName;
    }

    @Override
    public Fallible<Boolean> include() {
        if (this.queryParameterName == null || this.queryParameterName.isBlank()) {
            return new Err<>(new BadQueryParameterName(this.queryParameterName));
        }
        final Dict<String, Seq<String>> queryParams = this.msg.queryParameters();
        final Seq<String> values = queryParams.pick(this.queryParameterName)
            .orElse(new SeqOf<>());

        if (values.isEmpty()) {
            return new Err<>(new QueryParameterNotFound(this.queryParameterName));
        }

        final String value = values.pick(0).orElseThrow();

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return new Ok<>(value.equalsIgnoreCase("true"));
        }

        return new Err<>(new BadQueryParameterValue(value, Boolean.class));
    }
}
