package lorikeet.lobe;

import lorikeet.Tutorial;

public interface HttpReceptor<R extends UsesLogging> {
    HttpSignalFilter filter();
    void process(Tract<R> tract, HttpSignal signal);
}
