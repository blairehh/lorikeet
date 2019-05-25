package lorikeet.ecosphere.transcript;

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
