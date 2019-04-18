package lorikeet.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public final class Mapping {
    private final String path;

    public Mapping(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Mapping mapping = (Mapping) o;

        return Objects.equals(this.getPath(), mapping.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getPath());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("path", path)
            .toString();
    }
}
