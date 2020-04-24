package lorikeet.http.internal;

import lorikeet.core.stream.FStrop2;
import lorikeet.http.IncomingHttpSgnl;
import lorikeet.http.error.IncomingHttpSgnlError;
import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

public interface IncomingHttpSgnlStrop2<T, R extends UsesLogging & UsesCoding> extends FStrop2<IncomingHttpSgnl, Tract<R>, T, IncomingHttpSgnlError> {
}
