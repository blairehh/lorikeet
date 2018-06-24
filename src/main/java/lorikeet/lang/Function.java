package lorikeet.lang;

import java.util.Objects;
import java.util.Set;
import java.util.Optional;

public class Function {
    private final Type type;
    private final String name;
    private final Set<Attribute> attributes;
    private final Type returnType;
    private final Expression expression;


    public Function(Type type, String name, Set<Attribute> attributes, Type returnType) {
        this.type = type;
        this.name = name;
        this.attributes = attributes;
        this.returnType = returnType;
        this.expression = null;
    }

    public Function(
        Type type,
        String name,
        Set<Attribute> attributes,
        Type returnType,
        Expression e
    ) {
        this.type = type;
        this.name = name;
        this.attributes = attributes;
        this.returnType = returnType;
        this.expression = e;
    }

    public Function withExpression(Expression e) {
        return new Function(this.type, this.name, this.attributes, this.returnType, e);
    }

    public Type getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public Optional<Let> findLet(String name) {
        return this.expression.findLet(name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Function that = (Function)o;

        return Objects.equals(this.getType(), that.getType())
            && Objects.equals(this.getName(), that.getName())
            && Objects.equals(this.getAttributes(), that.getAttributes())
            && Objects.equals(this.getReturnType(), that.getReturnType())
            && Objects.equals(this.getExpression(), that.getExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.type,
            this.name,
            this.attributes,
            this.returnType,
            this.expression
        );
    }
}
