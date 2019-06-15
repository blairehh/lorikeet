package lorikeet.ecosphere.testing.data;

public class AnyValue implements Value {
    @Override
    public boolean isSymbolic() {
        return true;
    }

    @Override
    public int hashCode() {
        return AnyValue.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass().equals(AnyValue.class);
    }
}
