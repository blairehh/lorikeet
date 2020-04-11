package lorikeet.lobe;

import lorikeet.core.Fallible;

public interface HttpReceptor<R extends UsesLogging> {
    Fallible<Runnable> ligand(Tract<R> tract, IncomingHttpMsg msg);
}
