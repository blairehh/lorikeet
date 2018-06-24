package lorikeet;

import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.SpecType;
import lorikeet.lang.Expression;
import lorikeet.lang.Value;
import lorikeet.lang.Value.Variable;
import lorikeet.lang.Invoke;
import lorikeet.lang.SourceFile;
import lorikeet.lang.Struct;
import lorikeet.lang.Module;
import lorikeet.lang.LorikeetSource;
import lorikeet.lang.Attribute;
import lorikeet.lang.Function;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class Lang {

    public static Struct struct(Type type, Set<Attribute> attrs, List<Function> funcs) {
        return new Struct(type, attrs, funcs);
    }

    public static LorikeetSource lorikeetSource(SourceFile... source) {
        return new LorikeetSource(Arrays.asList(source), Collections.emptyList());
    }

    public static Package pkg(String a) {
        return new Package(a);
    }

    public static SourceFile sourceFile(Package pkg, List<Struct> structs, List<Module> modules) {
        return new SourceFile(pkg, structs, modules);
    }

    public static SpecType known(Type type) {
        return new SpecType.Known(type);
    }

    public static Invoke invoke(Value subject, String funcName, Value... args) {
        return new Invoke(subject, funcName, Arrays.asList(args));
    }

    public static Value.Invocation invocation(Value subject, String funcName, Value... args) {
        return new Value.Invocation(invoke(subject, funcName, args));
    }

    public static Value.Self self() {
        return new Value.Self();
    }

    public static Type type(String name, String... packages) {
        return new Type(new Package(packages), name);
    }

    public static Expression expression(Value value) {
        return new Expression(Arrays.asList(value), value.getExpressionType());
    }

    public static Value literal(int number) {
        return new Value.IntLiteral(String.valueOf(number));
    }

    public static Value literal(String value) {
        return new Value.StrLiteral(value);
    }

    public static Value literal(double number) {
        return new Value.DecLiteral(String.valueOf(number));
    }

    public static Value literal(boolean value) {
        return new Value.BolLiteral(String.valueOf(value));
    }

    public static Value.Variable variable(boolean param, String name, SpecType type) {
        return new Variable(param, name, type);
    }

    public static Value.Variable variable(String name, String type, String... packages) {
        return new Variable(false, name, known(type(type, packages)));
    }

    public static <A> List<A> listOf(A... values) {
        return Arrays.asList(values);
    }

    public static <A> Set<A> setOf(A... values) {
        return new HashSet<A>(Arrays.asList(values));
    }

}
