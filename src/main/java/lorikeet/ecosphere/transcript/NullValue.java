package lorikeet.ecosphere.transcript;

public class NullValue implements Value {

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass().equals(NullValue.class);
    }
}
