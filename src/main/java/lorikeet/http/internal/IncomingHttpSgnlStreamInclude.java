package lorikeet.http.internal;

import lorikeet.core.InputFallibleStreamInclude;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.http.error.IncomingHttpSgnlError;

public interface IncomingHttpSgnlStreamInclude<T> extends InputFallibleStreamInclude<IncomingHttpSgnl, T, IncomingHttpSgnlError> {
}
