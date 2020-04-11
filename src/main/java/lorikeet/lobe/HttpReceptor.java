package lorikeet.lobe;

import lorikeet.core.Fallible;

public interface HttpReceptor<R extends UsesLogging> {
    Fallible<Runnable> junction(Tract<R> tract, IncomingHttpMsg msg);
}
