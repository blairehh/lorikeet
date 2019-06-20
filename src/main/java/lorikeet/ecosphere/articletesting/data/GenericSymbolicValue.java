package lorikeet.ecosphere.articletesting.data;

import lorikeet.Seq;

import java.util.Objects;

public class GenericSymbolicValue implements Value {

    private final String name;
    private final Seq<Value> arguments;

    public GenericSymbolicValue(String name, Seq<Value> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return this.name;
    }

    public Seq<Value> getArguments() {
        return this.arguments;
    }

    @Override
    public Equality equality(Value other) {
        if (!other.isSymbolic()) {
            return Equality.NOT_EQUAL;
        }

        final GenericSymbolicValue symbol  = (GenericSymbolicValue)other;
        if (this.equals(symbol)) {
            return Equality.EQUAL;
        }
        return Equality.UNKNOWN;
    }

    @Override
    public boolean isSymbolic() {
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

        GenericSymbolicValue that = (GenericSymbolicValue) o;

        return Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getArguments(), that.getArguments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.getArguments());
    }
}
