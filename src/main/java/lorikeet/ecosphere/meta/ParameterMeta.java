package lorikeet.ecosphere.meta;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class ParameterMeta {
    private final int position;
    private final String name;
    private final boolean useHash;
    private final boolean ignore;
    private final Class<?> type;

    public ParameterMeta(int position, String name, boolean useHash, boolean ignore, Class<?> type) {
        this.position = position;
        this.name = parameterName(position, name);
        this.useHash = useHash;
        this.ignore = ignore;
        this.type = type;
    }



    public ParameterMeta(int position, String name, Class<?> type) {
        this.position = position;
        this.name = parameterName(position, name);
        this.useHash = false;
        this.ignore = false;
        this.type = type;
    }

    public ParameterMeta(int position, Class<?> type) {
        this.position = position;
        this.name = parameterName(position, null);
        this.useHash = false;
        this.ignore = false;
        this.type = type;
    }

    static String parameterName(int position, String suppliedName) {
        if (suppliedName == null || suppliedName.trim().isEmpty()) {
            return "-" + position;
        }
        return suppliedName;
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

    public Class<?> getType() {
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

        ParameterMeta that = (ParameterMeta) o;

        return Objects.equals(this.getPosition(), that.getPosition())
            && Objects.equals(this.isUseHash(), that.isUseHash())
            && Objects.equals(this.isIgnore(), that.isIgnore())
            && Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getPosition(), this.getName(), this.isUseHash(), this.isIgnore(), this.getType());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("position", this.getPosition())
            .append("name", this.getName())
            .append("useHash", this.isUseHash())
            .append("ignore", this.isIgnore())
            .append("type", this.getType())
            .toString();
    }
}
