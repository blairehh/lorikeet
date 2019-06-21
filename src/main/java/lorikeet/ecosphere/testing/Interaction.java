package lorikeet.ecosphere.testing;

import lorikeet.Opt;
import lorikeet.Seq;
import lorikeet.ecosphere.Cell;

import java.util.Objects;
import java.util.stream.Collectors;

public class Interaction {
    private final Cell cell;
    private final Seq<Object> arguments;
    private final Object returnValue;

    public Interaction(Cell cell, Seq<Object> arguments) {
        this.cell = cell;
        this.arguments = arguments;
        this.returnValue = null;
    }

    public Interaction(Cell cell, Seq<Object> arguments, Object returnValue) {
        this.cell = cell;
        this.arguments = arguments;
        this.returnValue = returnValue;
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

    public Opt<Object> getReturnValue() {
        return Opt.ofNullable(this.returnValue);
    }

    public Interaction withReturnValue(Object value) {
        return new Interaction(this.cell, this.arguments, value);
    }

    public boolean invokeEquals(Interaction interaction) {
        return Objects.equals(this.getCell().getClass(), interaction.getCell().getClass())
            && Objects.equals(this.getArguments(), interaction.getArguments());
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
        if (this.returnValue != null) {
            builder.append(" -> ");
            builder.append(this.returnValue.toString());
        }
        return builder.toString();
    }
}
