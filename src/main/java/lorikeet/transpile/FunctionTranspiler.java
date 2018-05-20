package lorikeet.transpile;

import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import java.util.Set;
import java.util.List;

public class FunctionTranspiler {

    private final TypeTranspiler type = new TypeTranspiler();

    public String transpileAbstractAll(List<Function> funcs) {
        StringBuilder builder = new StringBuilder();
        for (Function func : funcs) {
            builder.append(this.transpileAbstract(func));
            builder.append("\n");
        }
        return builder.toString();
    }

    public String transpileAbstract(Function func) {
        return String.format(
            " public %s %s(%s);",
            this.type.transpile(func.getReturnType()),
            func.getName(),
            this.parameters(func.getAttributes())
        );
    }

    public String transpileAll(List<Function> funcs) {
        StringBuilder builder = new StringBuilder();
        for (Function func : funcs) {
            builder.append(this.transpile(func));
            builder.append("\n");
        }
        return builder.toString();
    }

    public String transpile(Function func) {
        return String.format(
            " public %s %s(%s) {\n return  null;\n }",
            this.type.transpile(func.getReturnType()),
            func.getName(),
            this.parameters(func.getAttributes())
        );
    }

    private String parameters(Set<Attribute> attrs) {
        if (attrs.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Attribute attr: attrs) {
            builder.append("final ");
            builder.append(this.type.transpile(attr.getType()));
            builder.append(" v_");
            builder.append(attr.getName());
            builder.append(", ");
        }
        builder.replace(builder.length() - 2, builder.length(), "");
        return builder.toString();
    }
}
