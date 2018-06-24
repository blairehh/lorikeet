package lorikeet.lang;

import java.util.Optional;
import java.util.Objects;

public class Let implements Expressionable {
    private final String name;
    private final SpecType type;
    private final Expression expression;

    // Only used for testing
    public Let(String name, SpecType type, Expression expression) {
        this.name = name;
        this.type = type;
        this.expression = expression;
    }

    public Let(String name, Expression expression) {
        this.name = name;
        this.type = expression.getType();
        this.expression = expression;
    }

    public String getName() {
        return this.name;
    }

    public SpecType getType() {
        return this.type;
    }

    public Expression getExpression() {
        return this.expression;
    }

    @Override
    public Optional<SpecType> getExpressionType() {
        return Optional.empty();
    }

    @Override
    public void setExpressionType(SpecType.Known type) {
        // let does not return something so dont do anything. Probably should be an error.
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
            && Objects.equals(this.getExpression(), that.getExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.type, this.expression);
    }

    @Override
    public String toString() {
        return String.format("Let{name='%s' type=%s}", this.name, this.type);
    }
}
