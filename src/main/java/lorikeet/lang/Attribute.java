package lorikeet.lang;

import java.util.Objects;

public class Attribute {

    private final String name;
    private final Type type;

    public Attribute(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Attribute that = (Attribute)o;

        return Objects.equals(this.getName(), that.getName())
            && Objects.equals(that.getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.type);
    }

    @Override
    public String toString() {
        return String.format("%s %s,", this.name, this.type);
    }
}
