package lorikeet.lobe;

public interface HttpReceptor<R extends UsesLogging> {
    HttpLigand ligand(Tract<R> tract, IncomingHttpMsg msg);
    void receive(Tract<R> tract, IncomingHttpMsg signal);
}
