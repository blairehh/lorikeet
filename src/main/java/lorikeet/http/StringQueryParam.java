package lorikeet.http;

import lorikeet.core.Dict;
import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.core.Seq;
import lorikeet.core.SeqOf;
import lorikeet.http.error.BadQueryParameterName;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.error.QueryParameterNotFound;
import lorikeet.http.internal.IncomingHttpSgnlStreamInclude;

public class StringQueryParam implements IncomingHttpSgnlStreamInclude<String> {
    private final String queryParameterName;

    public StringQueryParam(String queryParameterName) {
        this.queryParameterName = queryParameterName;
    }

    @Override
    public FallibleResult<String, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
        if (this.queryParameterName == null || this.queryParameterName.isBlank()) {
            return new ErrResult<>(new BadQueryParameterName(this.queryParameterName));
        }
        final Dict<String, Seq<String>> queryParams = request.queryParameters();
        final Seq<String> values = queryParams.pick(this.queryParameterName)
            .orElse(new SeqOf<>());

        if (values.isEmpty()) {
            return new ErrResult<>(new QueryParameterNotFound(this.queryParameterName));
        }

        return new OkResult<>(values.pick(0).orElseThrow());
    }
}
