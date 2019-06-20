package lorikeet.ecosphere.articletesting.data;

public interface Value {
    Equality equality(Value other);
    default boolean isSymbolic() {
        return false;
    }
}
