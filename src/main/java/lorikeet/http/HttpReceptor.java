package lorikeet.http;

import lorikeet.lobe.Tract;
import lorikeet.lobe.UsesLogging;

public interface HttpReceptor<R extends UsesLogging> {
    HttpDirective junction(Tract<R> tract, IncomingHttpSgnl sgnl);
}
