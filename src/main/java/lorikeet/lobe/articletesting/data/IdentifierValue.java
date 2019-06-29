package lorikeet.lobe.articletesting.data;

import java.util.Objects;

public class IdentifierValue implements Value {
    private final String identifier;

    public IdentifierValue(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }

        if (!(other instanceof IdentifierValue)) {
            return Equality.NOT_EQUAL;
        }

        final IdentifierValue otherValue = (IdentifierValue)other;
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

        IdentifierValue that = (IdentifierValue) o;

        return Objects.equals(this.getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getIdentifier());
    }
}
