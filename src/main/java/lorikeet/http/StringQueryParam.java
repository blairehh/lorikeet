package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.Err;
import lorikeet.core.Fallible;
import lorikeet.core.IncludableFallible;
import lorikeet.core.Ok;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.error.BadQueryParameterName;
import lorikeet.http.error.QueryParameterNotFound;
import lorikeet.lobe.IncomingHttpMsg;

public class StringQueryParam implements IncludableFallible<String> {
    private final IncomingHttpMsg msg;
    private final String queryParameterName;

    public StringQueryParam(IncomingHttpMsg msg, String queryParameterName) {
        this.msg = msg;
        this.queryParameterName = queryParameterName;
    }

    @Override
    public Fallible<String> include() {
        if (this.queryParameterName == null || this.queryParameterName.isBlank()) {
            return new Err<>(new BadQueryParameterName(this.queryParameterName));
        }
        final Dict<String, Seq<String>> queryParams = this.msg.queryParameters();
        final Seq<String> values = queryParams.pick(this.queryParameterName)
            .orElse(new SeqOf<>());

        if (values.isEmpty()) {
            return new Err<>(new QueryParameterNotFound(this.queryParameterName));
        }

        return new Ok<>(values.pick(0).orElseThrow());
    }
}
