package lorikeet.ecosphere.testing;

import lorikeet.ecosphere.transcript.CellValue;
import lorikeet.ecosphere.transcript.Value;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class CellGraphNode {
    private CellValue cell;
    private final List<CellGraphNode> children;
    private final Instant timestamp;

    public CellGraphNode(CellValue cell, List<CellGraphNode> children, Instant timestamp) {
        this.cell = cell;
        this.children = children;
        this.timestamp = timestamp;
    }

    public CellValue getCell() {
        return this.cell;
    }

    public CellGraphNode setReturnValue(Value value) {
        this.cell = new CellValue(
            this.cell.getClassName(),
            this.cell.getArguments(),
            this.cell.getExceptionThrown().orElse(null),
            value
        );
        return this;
    }

    public CellGraphNode setExceptionThrown(Value value) {
        this.cell = new CellValue(
            this.cell.getClassName(),
            this.cell.getArguments(),
            value,
            this.cell.getReturnValue().orElse(null)
        );
        return this;
    }

    public List<CellGraphNode> getChildren() {
        return this.children;
    }

    public Instant getTimestamp() {
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

        CellGraphNode that = (CellGraphNode) o;

        return Objects.equals(this.getCell(), that.getCell())
            && Objects.equals(this.getChildren(), that.getChildren())
            && Objects.equals(this.getTimestamp(), that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCell(), this.getChildren(), this.getTimestamp());
    }
}
