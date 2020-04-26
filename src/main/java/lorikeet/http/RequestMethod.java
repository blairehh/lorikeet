package lorikeet.http;

import lorikeet.core.ErrResult;
import lorikeet.core.FallibleResult;
import lorikeet.core.OkResult;
import lorikeet.http.error.HttpMethodDoesNotMatchRequest;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.http.internal.IncomingHttpSgnlStrop;

public class RequestMethod implements IncomingHttpSgnlStrop<HttpMethod> {
    private final HttpMethod method;

    public RequestMethod(HttpMethod method) {
        this.method = method;
    }

    @Override
    public FallibleResult<HttpMethod, IncomingHttpSgnlError> include(IncomingHttpSgnl input) {
        if (input.method() != method) {
            return new ErrResult<>(new HttpMethodDoesNotMatchRequest());
        }
        return new OkResult<>(this.method);
    }
}
