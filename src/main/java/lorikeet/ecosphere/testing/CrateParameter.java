package lorikeet.ecosphere.testing;

import lorikeet.ecosphere.meta.ParameterMeta;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class CrateParameter {
    private final ParameterMeta meta;
    private final Object value;

    public CrateParameter(ParameterMeta meta, Object value) {
        this.meta = meta;
        this.value = value;
    }

    public ParameterMeta getMeta() {
        return this.meta;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        CrateParameter that = (CrateParameter) o;

        return Objects.equals(this.getMeta(), that.getMeta())
            && Objects.equals(this.getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getMeta(), this.getValue());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("meta", meta)
            .append("value", value)
            .toString();
    }
}
