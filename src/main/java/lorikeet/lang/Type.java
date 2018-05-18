package lorikeet.lang;

import lorikeet.lang.Package;
import java.util.Objects;

public class Type {

    private final Package pkg;
    private final String name;

    public Type(Package pkg, String name) {
        this.name = name;
        this.pkg = pkg;
    }

    public Package getPackage() {
        return this.pkg;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Type that = (Type)o;

        return Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getPackage(), that.getPackage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.pkg);
    }

    @Override
    public String toString() {
        return String.format("%s.%s", this.pkg, this.name);
    }
}
