package lorikeet.lang;

import java.util.List;
import java.util.Objects;

public class Expression {
    private final List<Expressionable> children;
    private final Type type;

    public Expression(List<Expressionable> children, Type type) {
        this.children = children;
        this.type = type;
    }

    public List<Expressionable> getChildren() {
        return this.children;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Expression that = (Expression)o;

        return Objects.equals(this.getChildren(), that.getChildren())
            && Objects.equals(this.getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.children, this.type);
    }
}
