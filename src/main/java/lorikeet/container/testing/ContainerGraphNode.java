package lorikeet.container.testing;

import lorikeet.Dict;
import lorikeet.Seq;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class ContainerGraphNode {
    private final String name;
    private final Dict<String, String> stringParameters;
    private final Dict<String, Number> numericParameters;
    private final Dict<String, Boolean> booleanParameters;
    private final Instant timestamp;
    private final List<ContainerGraphNode> children;

    public ContainerGraphNode(String name, Dict<String, String> stringParameters, Dict<String, Number> numericParameters,
                              Dict<String, Boolean> booleanParameters, Instant timestamp, List<ContainerGraphNode> children) {
        this.name = name;
        this.stringParameters = stringParameters;
        this.numericParameters = numericParameters;
        this.booleanParameters = booleanParameters;
        this.timestamp = timestamp;
        this.children = children;
    }

    public final String getName() {
        return this.name;
    }

    public final Dict<String, String> getStringParameters() {
        return this.stringParameters;
    }

    public final Dict<String, Number> getNumericParameters() {
        return this.numericParameters;
    }

    public final Dict<String, Boolean> getBooleanParameters() {
        return this.booleanParameters;
    }

    public final List<ContainerGraphNode> getChildren() {
        return this.children;
    }

    public final Instant getTimestamp() {
        return this.timestamp;
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
            && Objects.equals(this.getStringParameters(), that.getStringParameters())
            && Objects.equals(this.getNumericParameters(), that.getNumericParameters())
            && Objects.equals(this.getBooleanParameters(), that.getBooleanParameters())
            && Objects.equals(this.getTimestamp(), that.getTimestamp())
            && Objects.equals(this.getChildren(), that.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.getName(),
            this.getStringParameters(),
            this.getNumericParameters(),
            this.getBooleanParameters(),
            this.getTimestamp(),
            this.getChildren()
        );
    }
}
