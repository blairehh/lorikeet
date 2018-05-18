package lorikeet.lang;

import java.util.Optional;
import java.util.Objects;

public class Let implements Expressionable {
    private final String name;
    private final Type type;
    private final Value value;

    public Let(String name, Type type, Value value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public Value getValue() {
        return this.value;
    }

    @Override
    public Optional<Type> getExpressionType() {
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Let that = (Let)o;

        return Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getType(), that.getType())
            && Objects.equals(this.getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.type, this.value);
    }
}
