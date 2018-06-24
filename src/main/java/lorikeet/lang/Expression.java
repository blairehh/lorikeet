package lorikeet.lang;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Expression {
    private final List<Expressionable> children;
    private final SpecType type;

    public Expression(List<Expressionable> children, SpecType type) {
        this.children = children;
        this.type = type;
    }

    public List<Expressionable> getChildren() {
        return this.children;
    }

    public SpecType getType() {
        return this.type;
    }

    public Optional<Let> findLet(String name) {
        for (Expressionable e : this.children) {
            if (!(e instanceof Let)) {
                continue;
            }

            Let let = (Let)e;
            if (let.getName().equals(name)) {
                return Optional.of(let);
            }
        }
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

        Expression that = (Expression)o;

        return Objects.equals(this.getChildren(), that.getChildren())
            && Objects.equals(this.getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.children, this.type);
    }
}
