package lorikeet.ecosphere;

public interface ActionPotential<T> {
    T invoke();
    void connect(Tract tract);
}
