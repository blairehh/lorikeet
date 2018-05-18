package lorikeet.lang;

import java.util.Objects;
import java.util.Set;

public class Function {
    private final Type type;
    private final String name;
    private final Set<Attribute> attributes;
    private final Type returnType;


    public Function(Type type, String name, Set<Attribute> attributes, Type returnType) {
        this.type = type;
        this.name = name;
        this.attributes = attributes;
        this.returnType = returnType;
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
            && Objects.equals(this.getReturnType(), that.getReturnType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.name, this.attributes, this.returnType);
    }
}
