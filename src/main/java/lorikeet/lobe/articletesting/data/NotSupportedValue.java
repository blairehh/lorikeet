package lorikeet.lobe.articletesting.data;

public class NotSupportedValue implements Value {

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }

        if (!(other instanceof NotSupportedValue)) {
            return Equality.NOT_EQUAL;
        }

        return Equality.EQUAL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass().equals(NotSupportedValue.class);
    }

    @Override
    public int hashCode() {
        return NotSupportedValue.class.hashCode();
    }
}
