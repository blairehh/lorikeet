package lorikeet.lang;

import java.util.Objects;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class Struct implements LorikeetType {

    private final Origin origin;
    private final Type type;
    private final Set<Attribute> attributes;
    private final List<Function> functions;

    public Struct(File file, Type type, Set<Attribute> attributes) {
        this.origin = new Origin.Source(file);
        this.type = type;
        this.attributes = attributes;
        this.functions = new ArrayList<Function>();
    }

    public Struct(File file, Type type, Set<Attribute> attributes, List<Function> funcs) {
        this.origin = new Origin.Source(file);
        this.type = type;
        this.attributes = attributes;
        this.functions = funcs;
    }

    public Struct(Type type, Set<Attribute> attributes) {
        this.origin = null;
        this.type = type;
        this.attributes = attributes;
        this.functions = new ArrayList<Function>();
    }

    public Struct(Type type, Set<Attribute> attributes, List<Function> funcs) {
        this.origin = null;
        this.type = type;
        this.attributes = attributes;
        this.functions = funcs;
    }

    @Override
    public Origin getOrigin() {
        return this.origin;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Set<Attribute> getAttributes() {
        return this.attributes;
    }

    @Override
    public List<Function> getFunctions() {
        return this.functions;
    }

    public void addFunction(Function func) {
        this.functions.add(func);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Struct that = (Struct)o;

        return Objects.equals(this.getType(), that.getType())
            && Objects.equals(this.getAttributes(), that.getAttributes())
            && Objects.equals(this.getFunctions(), that.getFunctions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.origin, this.type, this.attributes, this.functions);
    }
}
