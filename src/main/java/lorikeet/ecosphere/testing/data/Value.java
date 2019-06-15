package lorikeet.ecosphere.testing.data;

public interface Value {
    default boolean isSymbolic() {
        return false;
    }
}
