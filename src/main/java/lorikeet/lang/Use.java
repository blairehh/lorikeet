package lorikeet.lang;

import java.util.Objects;

public class Use {
    private final Package pkg;
    private final String name;
    private final String alias;

    public Use(Package pkg, String name, String alias) {
        this.pkg = pkg;
        this.name = name;
        this.alias = alias;
    }

    public Package getPackage() {
        return this.pkg;
    }

    public String getName() {
        return this.name;
    }

    public String getAlias() {
        return this.alias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Use that = (Use)o;

        return Objects.equals(this.getPackage(), that.getPackage())
            && Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getAlias(), that.getAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.alias, this.pkg);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("use ");
        builder.append(this.name);
        builder.append(" of ");
        builder.append(this.pkg.toString());
        return builder.toString();
    }
}
