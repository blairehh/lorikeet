package lorikeet.ecosphere.testing.data;

public interface Value {
    Equality equality(Value other);
    default boolean isSymbolic() {
        return false;
    }
}
