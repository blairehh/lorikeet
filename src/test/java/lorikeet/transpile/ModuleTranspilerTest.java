package lorikeet.transpile;

import static lorikeet.Util.read;
import static lorikeet.Util.trim;

import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.Package;
import lorikeet.lang.Module;
import lorikeet.lang.Type;
import java.util.List;
import java.util.LinkedHashSet;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ModuleTranspilerTest {

    ModuleTranspiler transpiler = new ModuleTranspiler();

    @Test
    public void testNoAttr() {
        final LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        final Module module = new Module(new Type(new Package("app"), "Foo"), attrs);

        final List<JavaFile> files = transpiler.transpile(module);
        expect(files.get(0), "Lk_module_Foo.java", "noAttr.java");
    }

    @Test
    public void testOneAttr() {
        final LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        final Module module = new Module(new Type(new Package("app"), "Database"), attrs);
        attrs.add(new Attribute("conn", new Type(new Package("db", "conn"), "JdbcConnection")));

        final List<JavaFile> files = transpiler.transpile(module);
        expect(files.get(0), "Lk_module_Database.java", "oneAttr.java");
    }

    @Test
    public void testMainButNoRun() {
        final LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        final Module module = new Module(new Type(new Package("app"), "main"), attrs);
        attrs.add(new Attribute("conn", new Type(new Package("db", "conn"), "JdbcConnection")));
        attrs.add(new Attribute("io", new Type(new Package("app"), "TerminalIO")));
        attrs.add(new Attribute("log", new Type(new Package("logme"), "Logger")));

        final List<JavaFile> files = transpiler.transpile(module);
        expect(files.get(0), "Lk_module_main.java", "main.java");
    }

    @Test
    public void testMainConnectsJavaMainToRun() {
        final LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        final Module module = new Module(new Type(new Package("app"), "main"), attrs);
        attrs.add(new Attribute("conn", new Type(new Package("db", "conn"), "JdbcConnection")));
        attrs.add(new Attribute("io", new Type(new Package("app"), "TerminalIO")));
        attrs.add(new Attribute("log", new Type(new Package("logme"), "Logger")));

        final Function func1 = new Function(
            new Type(new Package("app"), "main"),
            "run",
            new LinkedHashSet<Attribute>(),
            new Type(new Package("lorikeet", "core"), "Int")
        );
        module.addFunction(func1);
        final List<JavaFile> files = transpiler.transpile(module);
        expect(files.get(0), "Lk_module_main.java", "testMainConnectsJavaMainToRun.java");
    }

    @Test
    public void testWithFunctions() {
        final LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        final Module module = new Module(new Type(new Package("app"), "Io"), attrs);

        final Function func1 = new Function(
            new Type(new Package("app"), "Io"),
            "read",
            new LinkedHashSet<Attribute>(),
            new Type(new Package("lorikeet", "core"), "Str")
        );
        module.addFunction(func1);

        LinkedHashSet<Attribute> params = new LinkedHashSet<Attribute>();
        params.add(new Attribute("value", new Type(new Package("lorikeet", "io"), "Printable")));
        final Function func2 = new Function(
            new Type(new Package("app"), "Io"),
            "print",
            params,
            new Type(new Package("lorikeet", "core"), "Str")
        );
        module.addFunction(func2);

        final List<JavaFile> files = transpiler.transpile(module);
        expect(files.get(0), "Lk_module_Io.java", "testWithFunctions.java");
    }


    void expect(JavaFile file, String fileName, String diskFile) {
        final String expected = read("lorikeet/transpile/ModuleTranspilerTest/" + diskFile);
        assertThat(trim(file.getContents())).isEqualTo(trim(expected));
        assertThat(file.getName()).isEqualTo(fileName);
    }
}
