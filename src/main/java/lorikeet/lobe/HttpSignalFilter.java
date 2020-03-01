package lorikeet.lobe;

public interface HttpSignalFilter {
    boolean matches(HttpSignal signal);
}
