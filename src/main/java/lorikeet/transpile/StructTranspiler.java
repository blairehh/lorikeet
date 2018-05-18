package lorikeet.transpile;

import lorikeet.lang.Attribute;
import lorikeet.lang.Struct;
import lorikeet.lang.Type;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class StructTranspiler {

    private final static String STRUCT_PREFIX = "Lk_struct_";

    private final TypeTranspiler type = new TypeTranspiler();
    private final FunctionTranspiler function = new FunctionTranspiler();

    public List<JavaFile> transpile(Struct struct) {
        ArrayList<JavaFile> files = new ArrayList<JavaFile>();
        files.add(this.concreteType(struct));
        files.add(this.abstractType(struct));
        return files;
    }

    private JavaFile abstractType(Struct struct) {
        final String type = struct.getType().getName();
        final String contents = String.format(
            "package %s;\n%s\npublic interface %s {\n%s\n%s\n%s}",
            struct.getType().getPackage().toString(),
            imports(),
            type,
            this.abstractGetters(struct.getAttributes()),
            this.abstractSetters(struct.getAttributes(), type),
            this.function.transpileAbstractAll(struct.getFunctions())
        );
        return new JavaFile(
            JavaUtils.subdirectoryFor(struct.getType().getPackage()),
            struct.getType().getName() + ".java",
            contents
        );
    }

    private String abstractGetters(Set<Attribute> attrs) {
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            final String code = String.format(
                " public %s get%s();\n",
                this.type.transpile(attr.getType()),
                attr.getName().substring(0, 1).toUpperCase() + attr.getName().substring(1)
            );
            builder.append(code);
        }
        return builder.toString();
    }

    private String abstractSetters(Set<Attribute> attrs, String javaInterface) {
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            final String code = String.format(
                " public <T extends %s> T set%s(%s value);\n",
                javaInterface,
                attr.getName().substring(0, 1).toUpperCase() + attr.getName().substring(1),
                this.type.transpile(attr.getType())
            );
            builder.append(code);
        }
        return builder.toString();
    }

    private JavaFile concreteType(Struct struct) {
        final String type = this.type.transpile(struct.getType());
        final String javaClassName = STRUCT_PREFIX + struct.getType().getName();
        final String contents = String.format(
            "package %s;\n%s\n%s\npublic class %s %s {\n%s\n%s\n%s\n%s\n%s\n%s\n%s}\n",
            struct.getType().getPackage().toString(),
            imports(),
            "@SuppressWarnings(\"unchecked\")",
            javaClassName,
            "implements " + struct.getType().getName(),
            this.fields(struct.getAttributes()),
            this.constructor(struct),
            this.getters(struct.getAttributes()),
            this.setters(struct.getAttributes(), javaClassName),
            this.function.transpileAll(struct.getFunctions()),
            equalsMethod(javaClassName, struct.getAttributes()),
            hashCodeMethod(struct.getAttributes())
        );

        return new JavaFile(
            JavaUtils.subdirectoryFor(struct.getType().getPackage()),
            STRUCT_PREFIX + struct.getType().getName() + ".java",
            contents
        );
    }

    private static String imports() {
        StringBuilder imports = new StringBuilder();
        imports.append("import java.util.Objects;");
        imports.append("\n");
        return imports.toString();
    }

    private String fields(Set<Attribute> attrs) {
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            final String code = String.format(
                " private final %s f_%s;\n",
                this.type.transpile(attr.getType()),
                attr.getName()
            );
            builder.append(code);
        }
        return builder.toString();
    }

    private String constructor(Struct struct) {
        return String.format(
            " public %s(%s) {\n%s }\n",
            STRUCT_PREFIX + struct.getType().getName(),
            this.arguments(struct.getAttributes()),
            this.inits(struct.getAttributes())
        );
    }

    private String arguments(Set<Attribute> attrs) {
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            final String code = String.format(
                "%s p_%s, ",
                this.type.transpile(attr.getType()),
                attr.getName()
            );
            builder.append(code);
        }
        if (builder.length() != 0) {
            builder.replace(builder.length() - 2, builder.length(), "");
        }
        return builder.toString();
    }

    private String inits(Set<Attribute> attrs) {
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            final String code = String.format(
                "  f_%s = p_%s;\n",
                attr.getName(),
                attr.getName()
            );
            builder.append(code);
        }
        return builder.toString();
    }

    private String getters(Set<Attribute> attrs) {
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            final String code = String.format(
                " @Override\n public %s get%s() {\n  return f_%s;\n }\n",
                this.type.transpile(attr.getType()),
                attr.getName().substring(0, 1).toUpperCase() + attr.getName().substring(1),
                attr.getName()
            );
            builder.append(code);
        }
        return builder.toString();
    }

    private String setters(Set<Attribute> attrs, String javaClass) {
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            final String code = String.format(
                " @Override\n public %s set%s(%s p_value) {\n  return %s;\n }\n",
                javaClass,
                attr.getName().substring(0, 1).toUpperCase() + attr.getName().substring(1),
                this.type.transpile(attr.getType()),
                constructorCall(attrs, attr.getName(), javaClass)
            );
            builder.append(code);
        }
        return builder.toString();
    }

    private String constructorCall(Set<Attribute> attrs, String attrName, String javaClass) {
        StringBuilder builder = new StringBuilder();
        builder.append("new ");
        builder.append(javaClass);
        builder.append("(");
        for (Attribute attr : attrs) {
            if (attr.getName().equals(attrName)) {
                builder.append("p_value");
            } else {
                builder.append("f_");
                builder.append(attr.getName());
            }
            builder.append(", ");
        }
        builder.replace(builder.length() - 2, builder.length() , "");
        builder.append(")");
        return builder.toString();
    }

    private String equalsMethod(String javaClassName, Set<Attribute> attrs) {
        if (attrs.isEmpty()) {
            return emptyEqualsMethod();
        }
        final String format = " @Override\n"
            + " public boolean equals(Object p_o) {\n"
            + "  if (p_o == this) {\n"
            + "   return true;\n"
            + "  }\n"
            + "  if (p_o == null || !this.getClass().equals(p_o.getClass())) {\n"
            + "   return false;\n"
            + "  }\n"
            + "  %s that = (%s)p_o;\n"
            + "  return (\n"
            + "  %s"
            + "  );\n"
            + " }\n";
        return String.format(format, javaClassName, javaClassName, comparisons(attrs));
    }

    private static String emptyEqualsMethod() {
        return " @Override\n"
            + " public boolean equals(Object p_o) {\n"
            + "  if (p_o == this) {\n"
            + "   return true;\n"
            + "  }\n"
            + "  if (p_o == null || !this.getClass().equals(p_o.getClass())) {\n"
            + "   return false;\n"
            + "  }\n"
            + "  return true;\n"
            + " }\n";
    }

    private String comparisons(Set<Attribute> attrs) {
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            final String code = String.format(
                "   Objects.equals(f_%s, that.f_%s) &&\n",
                attr.getName(),
                attr.getName()
            );
            builder.append(code);
        }
        final String comparisons = builder.toString();
        if (builder.length() != 0) {
            builder.replace(builder.length() - 3, builder.length() - 1, "");
        }
        return builder.toString();
    }

    private String hashCodeMethod(Set<Attribute> attrs) {
        if (attrs.isEmpty()) {
            return emptyHashCodeMethod();
        }
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            builder.append(String.format("f_%s, ", attr.getName()));
        }
        if (builder.length() != 0) {
            builder.replace(builder.length() - 2, builder.length(), "");
        }
        final String format = " @Override\n"
            + " public int hashCode() {\n"
            + "  return Objects.hash(%s);\n"
            + " }\n";
        return String.format(format, builder.toString());
    }

    public static String emptyHashCodeMethod() {
        return " @Override\n"
            + " public int hashCode() {\n"
            + "  return 0;\n"
            + " }\n";
    }

}
