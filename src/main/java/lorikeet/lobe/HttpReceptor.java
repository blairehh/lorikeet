package lorikeet.lobe;

public interface HttpReceptor<R extends UsesLogging> {
    HttpLigand ligand();
    void receive(Tract<R> tract, IncomingHttpMsg signal);
}
