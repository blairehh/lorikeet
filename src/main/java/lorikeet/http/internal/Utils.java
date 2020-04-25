package lorikeet.http.internal;

import lorikeet.core.Seq;
import lorikeet.http.HttpStatus;
import lorikeet.http.error.IncomingHttpSgnlError;

public class Utils {
    public HttpStatus statusFor(Seq<? extends IncomingHttpSgnlError> errors) {
        if (errors.isEmpty()) {
            return HttpStatus.OK;
        }
        final Seq<HttpStatus> statuses = errors.remodel(IncomingHttpSgnlError::rejectStatus);
        final HttpStatus first = statuses.first().orElseThrow();
        if (statuses.uniform()) {
            return first;
        }
        if (statuses.contains(HttpStatus.BAD_REQUEST)) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
