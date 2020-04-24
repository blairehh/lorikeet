package lorikeet.http.error;

import lorikeet.http.HttpStatus;

public class HttpMethodDoesNotMatchRequest extends IncomingHttpSgnlError {

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(HttpMethodDoesNotMatchRequest.class);
    }

    @Override
    public HttpStatus rejectStatus() {
        return HttpStatus.METHOD_NOT_ALLOWED;
    }

    @Override
    public int hashCode() {
        return HttpMethodDoesNotMatchRequest.class.hashCode();
    }
}
