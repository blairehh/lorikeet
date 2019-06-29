package lorikeet.lobe.articletesting.data;

public class NullValue implements Value {

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }

        if (!(other instanceof NullValue)) {
            return Equality.NOT_EQUAL;
        }

        return Equality.EQUAL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass().equals(NullValue.class);
    }

    @Override
    public int hashCode() {
        return NullValue.class.hashCode();
    }
}
