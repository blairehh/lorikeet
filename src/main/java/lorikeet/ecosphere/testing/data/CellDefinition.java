package lorikeet.ecosphere.testing.data;

import lorikeet.Dict;
import lorikeet.Opt;

import java.util.Objects;

public class CellDefinition {
    private final String className;
    private final String exceptionThrown;
    private final Value returnValue;
    private final Dict<String, Value> arguments;

    public CellDefinition(String className, Dict<String, Value> arguments, String exceptionThrown, Value returnValue) {
        this.className = className;
        this.arguments = arguments;
        this.exceptionThrown = exceptionThrown;
        this.returnValue = returnValue;
    }

    public String getClassName() {
        return this.className;
    }

    public Dict<String, Value> getArguments() {
        return this.arguments;
    }

    public Opt<String> getExceptionThrown() {
        return Opt.ofNullable(this.exceptionThrown);
    }

    public Opt<Value> getReturnValue() {
        return Opt.ofNullable(this.returnValue);
    }

    public CellDefinition withReturnValue(Value value) {
        return new CellDefinition(this.className, this.arguments, this.exceptionThrown, value);
    }

    public CellDefinition withExceptionThrown(String exception) {
        return new CellDefinition(this.className, this.arguments, exception, this.returnValue);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        CellDefinition that = (CellDefinition) o;

        return Objects.equals(this.getClassName(), that.getClassName())
            && Objects.equals(this.getArguments(), that.getArguments())
            && Objects.equals(this.getExceptionThrown(), that.getExceptionThrown())
            && Objects.equals(this.getReturnValue(), that.getReturnValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClassName(), this.getArguments(), this.getExceptionThrown(), this.getReturnValue());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.className);
        builder.append("(");

        for (Value value : this.arguments.values()) {
            builder.append(value.toString());
        }
        builder.append(")");

        if (this.returnValue != null) {
            builder.append(" returns ");
            builder.append(this.returnValue.toString());
        }

        if (this.exceptionThrown!= null) {
            builder.append(" throws ");
            builder.append(this.exceptionThrown);
        }

        return builder.toString();
    }
}
