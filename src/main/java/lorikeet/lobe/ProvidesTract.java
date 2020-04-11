package lorikeet.lobe;

public interface ProvidesTract <R extends UsesLogging> {
    Tract<R> provideTract();
}
