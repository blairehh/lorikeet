package lorikeet.sdk.types;

import lorikeet.lang.Attribute;
import lorikeet.lang.Origin;
import lorikeet.lang.Function;
import lorikeet.lang.LorikeetType;
import lorikeet.lang.Package;
import lorikeet.lang.Type;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Objects;

public class Dec implements LorikeetType {
    private static final Origin.Sdk origin;
    private static final Type type;
    private static final Set<Attribute> attrs;
    private static final List<Function> funcs;

    static {
        origin = new Origin.Sdk("1");
        type = new Type(new Package("lorikeet", "core"), "Dec");
        attrs = Collections.emptySet();
        funcs = Collections.emptyList();
    }

    public static Type type() {
        return type;
    }

    @Override
    public Origin getOrigin() {
        return origin;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Set<Attribute> getAttributes() {
        return attrs;
    }

    @Override
    public List<Function> getFunctions() {
        return funcs;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        return (o != null || this.getClass().equals(o.getClass()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, type, attrs, funcs);
    }
}
