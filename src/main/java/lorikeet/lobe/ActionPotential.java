package lorikeet.lobe;

public interface ActionPotential<T> {
    T invoke();
    void connect(Tract tract);
}
