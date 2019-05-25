package lorikeet.ecosphere.testing.data;

public class NullValue implements Value {

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass().equals(NullValue.class);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
