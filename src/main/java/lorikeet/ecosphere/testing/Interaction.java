package lorikeet.ecosphere.testing;

import lorikeet.Seq;
import lorikeet.ecosphere.Cell;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;
import java.util.stream.Collectors;

public class Interaction {
    private final Cell cell;
    private final Seq<Object> arguments;

    public Interaction(Cell cell, Seq<Object> arguments) {
        this.cell = cell;
        this.arguments = arguments;
    }

    public static Interaction of(Cell cell, Object... arguments) {
        return new Interaction(cell, Seq.of(arguments));
    }

    public Cell getCell() {
        return this.cell;
    }

    public Seq<Object> getArguments() {
        return this.arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Interaction that = (Interaction) o;

        return Objects.equals(this.getCell().getClass(), that.getCell().getClass())
            && Objects.equals(this.getArguments(), that.getArguments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCell(), this.getArguments());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.cell.getClass().getName());
        builder.append("(");
        final String args = this.arguments.stream()
            .map(Object::toString)
            .collect(Collectors.joining(","));
        builder.append(args);
        builder.append(")");
        return builder.toString();
    }
}
