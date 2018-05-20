package lorikeet.transpile;

import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.Package;
import lorikeet.lang.Module;
import lorikeet.lang.Type;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class ModuleTranspiler {

    private final static String MODULE_PREFIX = "Lk_module_";

    private final TypeTranspiler type = new TypeTranspiler();
    private final FunctionTranspiler function = new FunctionTranspiler();

    public List<JavaFile> transpile(Module module) {
        ArrayList<JavaFile> files = new ArrayList<JavaFile>();
        files.add(this.concreteType(module));
        return files;
    }

    private JavaFile concreteType(Module module) {
        final String type = module.getType().getName();
        final String javaClassName = MODULE_PREFIX + type;
        final String contents = String.format(
            "package %s;\npublic class %s {\n%s\n%s\n%s\n%s}\n",
            module.getType().getPackage().toString(),
            javaClassName,
            this.fields(module.getAttributes()),
            this.constructor(javaClassName, module),
            this.function.transpileAll(module.getFunctions()),
            this.specialFunctions(module)
        );
        return new JavaFile(
            JavaUtils.subdirectoryFor(module.getType().getPackage()),
            MODULE_PREFIX + type + ".java",
            contents
        );
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

    private String constructor(String javaClass, Module module) {
        if (module.getAttributes().isEmpty()) {
            return String.format(" public %s() {\n}\n", javaClass);
        }
        return String.format(
            " public %s(\n%s\n ) {\n%s }\n",
            javaClass,
            this.arguments(module.getAttributes()),
            this.inits(module.getAttributes())
        );
    }

    private String arguments(Set<Attribute> attrs) {
        StringBuilder builder = new StringBuilder();
        for (Attribute attr : attrs) {
            final String code = String.format(
                "  %s v_%s,\n",
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
                "  f_%s = v_%s;\n",
                attr.getName(),
                attr.getName()
            );
            builder.append(code);
        }
        return builder.toString();
    }

    private String specialFunctions(Module module) {

        if (this.implementMainMethod(module.getFunctions())) {
            return " public static void main(String[] args) {\nnew Lk_module_main().run();\n }\n";
        }
        return "";
    }

    private boolean implementMainMethod(List<Function> functions) {
        for (Function func : functions) {
            if (!func.getType().getName().equals("main")) {
                continue;
            }
            if (!func.getName().equals("run")) {
                continue;
            }
            if (!func.getAttributes().isEmpty()) {
                continue;
            }
            if (func.getReturnType().equals(new Type(new Package("lorikeet", "core"), "Int"))) {
                return true;
            }
        }
        return false;
    }

}
