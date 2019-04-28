package lorikeet.ecosphere.meta;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class ParameterMeta {
    private final int position;
    private final String name;
    private final boolean useHash;
    private final boolean ignore;

    public ParameterMeta(int position, String name, boolean useHash, boolean ignore) {
        this.position = position;
        this.name = name;
        this.useHash = useHash;
        this.ignore = ignore;
    }

    public int getPosition() {
        return this.position;
    }

    public String getName() {
        return this.name;
    }

    public boolean isUseHash() {
        return this.useHash;
    }

    public boolean isIgnore() {
        return this.ignore;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        ParameterMeta that = (ParameterMeta) o;

        return Objects.equals(this.getPosition(), that.getPosition())
            && Objects.equals(this.isUseHash(), that.isUseHash())
            && Objects.equals(this.isIgnore(), that.isIgnore())
            && Objects.equals(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getPosition(), this.getName(), this.isUseHash(), this.isIgnore());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("position", this.getPosition())
            .append("name", this.getName())
            .append("useHash", this.isUseHash())
            .append("ignore", this.isIgnore())
            .toString();
    }
}
