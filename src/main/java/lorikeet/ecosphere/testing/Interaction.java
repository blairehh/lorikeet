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
    private final Class<? extends Exception> exceptionThrown;

    public Interaction(Cell cell, Seq<Object> arguments) {
        this.cell = cell;
        this.arguments = arguments;
        this.returnValue = null;
        this.exceptionThrown = null;
    }

    public Interaction(Cell cell, Seq<Object> arguments, Object returnValue, Class<? extends Exception> exception) {
        this.cell = cell;
        this.arguments = arguments;
        this.returnValue = returnValue;
        this.exceptionThrown = exception;
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

    public Opt<Class<? extends Exception>> getExceptionThrown() {
        return Opt.ofNullable(this.exceptionThrown);
    }

    public Interaction withReturnValue(Object value) {
        return new Interaction(this.cell, this.arguments, value, this.exceptionThrown);
    }

    public Interaction withExceptionThrown(Class<? extends Exception> exception) {
        return new Interaction(this.cell, this.arguments, this.returnValue, exception);
    }

    public boolean invokeEquals(Interaction interaction) {
        return Objects.equals(this.getCell().getClass(), interaction.getCell().getClass())
            && Objects.equals(this.getArguments(), interaction.getArguments());
    }

    public boolean outcomeIsApplicableTo(Interaction interaction) {
        if (interaction.getExceptionThrown().isPresent()) {
            return interaction.getExceptionThrown()
                .map(ex -> Objects.equals(ex, this.exceptionThrown))
                .orElse(true);
        }

        if (interaction.getReturnValue().isPresent()) {
            return interaction.getReturnValue()
                .map(value -> Objects.equals(value, this.returnValue))
                .orElse(true);
        }

        return true;
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
            && Objects.equals(this.getArguments(), that.getArguments())
            && Objects.equals(this.getReturnValue(), that.getReturnValue())
            && Objects.equals(this.getExceptionThrown(), that.getExceptionThrown());
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
        if (this.exceptionThrown != null) {
            builder.append(" ! ");
            builder.append(this.exceptionThrown.getName());
        }
        return builder.toString();
    }
}
