package lorikeet.http;

import lorikeet.core.FallibleResult;
import lorikeet.http.error.IncomingHttpSgnlError;

public interface HttpDirective extends FallibleResult<HttpReplier, IncomingHttpSgnlError> {
}
