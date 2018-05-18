package lorikeet.transpile;

import static lorikeet.Util.trim;

import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.Package;
import lorikeet.lang.Type;

import org.junit.Test;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionTranspilerTest {

    FunctionTranspiler transpiler = new FunctionTranspiler();

    @Test
    public void testNoParams() {
        Function func = new Function(
            new Type(new Package("app"), "Foo"),
            "bar",
            new LinkedHashSet<Attribute>(),
            new Type(new Package("lorikeet", "core"), "Str")
        );

        String source = this.transpiler.transpile(func);

        expect(source, " public lorikeet.core.Str bar() { return null; }");
    }

    @Test
    public void testAllFuncs() {
        Function func1 = new Function(
            new Type(new Package("app"), "Foo"),
            "bar",
            new LinkedHashSet<Attribute>(),
            new Type(new Package("lorikeet", "core"), "Str")
        );

        Function func2 = new Function(
            new Type(new Package("app"), "Foo"),
            "bar",
            new LinkedHashSet<Attribute>(),
            new Type(new Package("lorikeet", "core"), "Str")
        );

        String source = this.transpiler.transpileAll(Arrays.asList(func1, func2));

        expect(source, " public lorikeet.core.Str bar() { return null; }\n public lorikeet.core.Str bar() { return null; }");
    }

    @Test
    public void testOneParam() {
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        attrs.add(new Attribute("baz", new Type(new Package("lorikeet", "core"), "Int")));
        Function func = new Function(
            new Type(new Package("app"), "Foo"),
            "bar",
            attrs,
            new Type(new Package("lorikeet", "core"), "Str")
        );

        String source = this.transpiler.transpile(func);

        expect(source, " public lorikeet.core.Str bar(final lorikeet.core.Int p_baz) { return null; }");
    }

    @Test
    public void testVoidFunc() {
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        attrs.add(new Attribute("baz", new Type(new Package("lorikeet", "core"), "Int")));
        Function func = new Function(
            new Type(new Package("app"), "Foo"),
            "bar",
            attrs,
            new Type(new Package("java", "lang"), "Void")
        );

        String source = this.transpiler.transpile(func);

        expect(source, " public java.lang.Void bar(final lorikeet.core.Int p_baz) { return null; }");
    }


    @Test
    public void testTwoParam() {
        Set<Attribute> attrs = new LinkedHashSet<Attribute>();
        attrs.add(new Attribute("baz", new Type(new Package("lorikeet", "core"), "Int")));
        attrs.add(new Attribute("wom", new Type(new Package("a", "b", "c"), "D")));
        Function func = new Function(
            new Type(new Package("app"), "Foo"),
            "bar",
            attrs,
            new Type(new Package("lorikeet", "core"), "Str")
        );

        String source = this.transpiler.transpile(func);

        expect(
            source,
            " public lorikeet.core.Str bar(final lorikeet.core.Int p_baz, final a.b.c.D p_wom) { return null; }"
        );
    }

    void expect(String generated, String expected) {
        assertThat(trim(generated)).isEqualTo(trim(expected));
    }
}
