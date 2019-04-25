package lorikeet.ecosphere.testing;


import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class CrateGraphNode {
    private final String name;
    private final List<CrateParameter> parameters;
    private final Instant timestamp;
    private final int serializedHashCode;
    private final List<CrateGraphNode> children;

    public CrateGraphNode(String name, List<CrateParameter> parameters, Instant timestamp,
                          List<CrateGraphNode> children) {
        this.name = name;
        this.parameters = parameters;
        this.serializedHashCode = Objects.hash(parameters.toArray());
        this.timestamp = timestamp;
        this.children = children;
    }

    public final String getName() {
        return this.name;
    }

    public final List<CrateParameter> getParameters() {
        return this.parameters;
    }

    public final List<CrateGraphNode> getChildren() {
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

        CrateGraphNode that = (CrateGraphNode) o;

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
