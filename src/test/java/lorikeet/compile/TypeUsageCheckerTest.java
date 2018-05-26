package lorikeet.compile;

import lorikeet.error.CompileError;
import lorikeet.error.UnkownType;
import lorikeet.lang.Attribute;
import lorikeet.lang.LorikeetSource;
import lorikeet.lang.Module;
import lorikeet.lang.Function;
import lorikeet.lang.Package;
import lorikeet.lang.SourceFile;
import lorikeet.lang.Struct;
import lorikeet.lang.Type;

import java.util.Collections;
import java.util.Arrays;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeUsageCheckerTest {


    @Test
    public void testStructAttribute() {
        Set<Attribute> attrs = setOf(
            attribute("bar", type("Bar"))
        );
        Struct struct1 = struct(type("Foo"), attrs);
        SourceFile sourceFile = sourceFile(Arrays.asList(struct1));

        List<CompileError> errors = check(sourceFile);

        expect(errors, sourceFile, type("Bar"));
    }


    @Test
    public void testFuncReturn() {
        Set<Attribute> attrs = setOf(
            attribute("bar", type("Bar"))
        );
        Struct struct = struct(type("Foo"), attrs);
        SourceFile sourceFile = sourceFile(Arrays.asList(struct));

        List<CompileError> errors = check(sourceFile);

        expect(errors, sourceFile, type("Bar"));
    }


    @Test
    public void testFunction() {
        Struct struct = struct(type("Foo"), Collections.emptySet());
        struct.addFunction(func(type("Foo"), "baz", type("Bar")));
        SourceFile sourceFile = sourceFile(Arrays.asList(struct));

        List<CompileError> errors = check(sourceFile);

        expect(errors, sourceFile, type("Bar"));
    }

    @Test
    public void testFunctionParameters() {
        Struct struct = struct(type("Foo"), Collections.emptySet());
        final Set<Attribute> params = setOf(
            attribute("foo", type("Foo")),
            attribute("bar", type("Bar"))
        );
        struct.addFunction(func(type("Foo"), "baz", params, type("Foo")));
        SourceFile sourceFile = sourceFile(Arrays.asList(struct));

        List<CompileError> errors = check(sourceFile);

        expect(errors, sourceFile, type("Bar"));
    }

    @Test
    public void testModuleAttribute() {
        Set<Attribute> attrs = setOf(
            attribute("bar", type("Bar"))
        );
        Module module = module(type("Foo"), attrs);
        SourceFile sourceFile = sourceFile(Collections.emptyList(), Arrays.asList(module));

        List<CompileError> errors = check(sourceFile);

        expect(errors, sourceFile, type("Bar"));
    }

    void expect(List<CompileError> errors) {
        assertThat(errors.size()).isEqualTo(0);
    }

    void expect(List<CompileError> errors, SourceFile sourceFile, Type... types) {
        List<CompileError> unkowns = Arrays.asList(types)
            .stream()
            .map(type -> new UnkownType(type, sourceFile))
            .collect(Collectors.toList());

        assertThat(errors).isEqualTo(unkowns);
    }


    static List<CompileError> check(SourceFile sourceFile) {
        return new TypeUsageChecker().check(new LorikeetSource(
            Arrays.asList(sourceFile),
            Collections.emptyList()
        ));
    }

    static<T> Set<T> setOf(T... items) {
        return new LinkedHashSet<T>(Arrays.asList(items));
    }

    static Function func(Type type, String name, Type retrn) {
        return new Function(type, name, Collections.emptySet(), retrn);
    }

    static Function func(Type type, String name, Set<Attribute> attrs, Type retrn) {
        return new Function(type, name, attrs, retrn);
    }

    static Struct struct(Type type, Set<Attribute> attrs) {
        return new Struct(type, attrs);
    }

    static Module module(Type type, Set<Attribute> attrs) {
        return new Module(type, attrs);
    }

    static Attribute attribute(String name, Type type) {
        return new Attribute(name, type);
    }

    static Type type(String name) {
        return new Type(new Package("test"), name);
    }

    static SourceFile sourceFile(List<Struct> structs) {
        return new SourceFile(new Package("test"), structs, Collections.emptyList());
    }

    static SourceFile sourceFile(List<Struct> structs, List<Module> modules) {
        return new SourceFile(new Package("test"), structs, modules);
    }
}
