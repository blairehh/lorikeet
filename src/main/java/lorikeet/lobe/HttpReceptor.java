package lorikeet.lobe;

import lorikeet.http.HttpDirective;

public interface HttpReceptor<R extends UsesLogging> {
    HttpDirective junction(Tract<R> tract, IncomingHttpMsg msg);
}
