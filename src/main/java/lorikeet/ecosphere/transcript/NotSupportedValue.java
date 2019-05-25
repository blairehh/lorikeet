package lorikeet.ecosphere.transcript;

public class NotSupportedValue implements Value {

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass().equals(NotSupportedValue.class);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
