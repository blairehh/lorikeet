package lorikeet.lobe.articletesting.data;

import java.util.Objects;

public class HashValue implements Value {
    private final String className;
    private final String hashValue;

    public HashValue(String className, String hashValue) {
        this.className = className;
        this.hashValue = hashValue;
    }

    public String getClassName() {
        return this.className;
    }

    public String getHashValue() {
        return this.hashValue;
    }

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }

        if (!(other instanceof HashValue)) {
            return Equality.NOT_EQUAL;
        }

        final HashValue otherValue = (HashValue)other;
        if (this.equals(otherValue)) {
            return Equality.EQUAL;
        }
        return Equality.NOT_EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        HashValue hashRelic = (HashValue) o;

        return Objects.equals(this.getClassName(), hashRelic.getClassName())
            && Objects.equals(this.getHashValue(), hashRelic.getHashValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClassName(), this.getHashValue());
    }
}
