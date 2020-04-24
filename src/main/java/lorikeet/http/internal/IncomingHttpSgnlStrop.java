package lorikeet.http.internal;

import lorikeet.core.stream.FStrop1;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.http.error.IncomingHttpSgnlError;

public interface IncomingHttpSgnlStrop<T> extends FStrop1<IncomingHttpSgnl, T, IncomingHttpSgnlError> {
}
