package lorikeet.container.testing;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class ContainerParameter {
    private final RenderType renderType;
    private final String name;
    private final String value;

    public ContainerParameter(RenderType renderType, String name, String value) {
        this.renderType = renderType;
        this.name = name;
        this.value = value;
    }

    public RenderType getRenderType() {
        return this.renderType;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
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

        ContainerParameter that = (ContainerParameter) o;

        return Objects.equals(this.getRenderType(), that.getRenderType())
            && Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getValue());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("renderType", renderType)
            .append("name", name)
            .append("value", value)
            .toString();
    }
}
