package lorikeet.http;

import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesCoding;
import lorikeet.lobe.UsesLogging;

public interface HttpReceptor<R extends UsesLogging & UsesCoding> {
    HttpDirective junction(Tract<R> tract, IncomingHttpSgnl sgnl);
}
