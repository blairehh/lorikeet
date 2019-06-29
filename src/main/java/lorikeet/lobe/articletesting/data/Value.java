package lorikeet.lobe.articletesting.data;

public interface Value {
    Equality equality(Value other);
    default boolean isSymbolic() {
        return false;
    }
}
