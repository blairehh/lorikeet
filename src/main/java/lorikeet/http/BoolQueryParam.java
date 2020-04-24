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
import lorikeet.http.internal.IncomingHttpSgnlStreamInclude;

import java.util.Objects;

public class BoolQueryParam implements IncomingHttpSgnlStreamInclude<Boolean> {
    private final String queryParameterName;

    public BoolQueryParam(String queryParameterName) {
        this.queryParameterName = queryParameterName;
    }

    @Override
    public FallibleResult<Boolean, IncomingHttpSgnlError> include(IncomingHttpSgnl request) {
        if (this.queryParameterName == null || this.queryParameterName.isBlank()) {
            return new ErrResult<>(new BadQueryParameterName(this.queryParameterName));
        }
        final Dict<String, Seq<String>> queryParams = request.queryParameters();
        final Seq<String> values = queryParams.pick(this.queryParameterName)
            .orElse(new SeqOf<>());

        if (values.isEmpty()) {
            return new ErrResult<>(new QueryParameterNotFound(this.queryParameterName));
        }

        final String value = values.pick(0).orElseThrow();

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return new OkResult<>(value.equalsIgnoreCase("true"));
        }

        return new ErrResult<>(new BadQueryParameterValue(value, Boolean.class));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BoolQueryParam that = (BoolQueryParam) o;

        return Objects.equals(this.queryParameterName, that.queryParameterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.queryParameterName);
    }
}
