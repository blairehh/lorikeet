package lorikeet.container.testing;


import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class ContainerGraphNode {
    private final String name;
    private final List<ContainerParameter> parameters;
    private final Instant timestamp;
    private final int serializedHashCode;
    private final List<ContainerGraphNode> children;

    public ContainerGraphNode(String name, List<ContainerParameter> parameters, Instant timestamp,
                              List<ContainerGraphNode> children) {
        this.name = name;
        this.parameters = parameters;
        this.serializedHashCode = Objects.hash(parameters.toArray());
        this.timestamp = timestamp;
        this.children = children;
    }

    public final String getName() {
        return this.name;
    }

    public final List<ContainerParameter> getParameters() {
        return this.parameters;
    }

    public final List<ContainerGraphNode> getChildren() {
        return this.children;
    }

    public final Instant getTimestamp() {
        return this.timestamp;
    }

    public int getSerializedHashCode() {
        return this.serializedHashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        ContainerGraphNode that = (ContainerGraphNode) o;

        return Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getParameters(), that.getParameters())
            && Objects.equals(this.getTimestamp(), that.getTimestamp())
            && Objects.equals(this.getChildren(), that.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.getName(),
            this.getParameters(),
            this.getTimestamp(),
            this.getChildren()
        );
    }
}
