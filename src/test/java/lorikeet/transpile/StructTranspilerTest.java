package lorikeet.transpile;

import static lorikeet.Util.read;
import static lorikeet.Util.trim;

import lorikeet.lang.Attribute;
import lorikeet.lang.Function;
import lorikeet.lang.Package;
import lorikeet.lang.Struct;
import lorikeet.lang.Type;
import java.util.List;
import java.util.LinkedHashSet;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class StructTranspilerTest {

    StructTranspiler transpiler = new StructTranspiler();

    @Test
    public void testOneAttr() {

        final LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        attrs.add(new Attribute("name", new Type(new Package("lorikeet", "core"), "Str")));
        final Struct struct = new Struct(new Type(new Package("test"), "User"), attrs);

        final List<JavaFile> files = transpiler.transpile(struct);
        expect(files.get(0), "Lk_struct_User.java", "testOneAttr.concrete.java");
        expect(files.get(1), "User.java", "testOneAttr.abstract.java");
    }

    @Test
    public void testNoAttr() {

        final LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        final Struct struct = new Struct(new Type(new Package("test"), "User"), attrs);

        final List<JavaFile> files = transpiler.transpile(struct);
        expect(files.get(0), "Lk_struct_User.java", "testNoAttr.concrete.java");
        expect(files.get(1), "User.java", "testNoAttr.abstract.java");
    }

    @Test
        public void testThreeAttr() {

        final LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        attrs.add(new Attribute("name", new Type(new Package("lorikeet", "core"), "Str")));
        attrs.add(new Attribute("role", new Type(new Package("test"), "Role")));
        attrs.add(new Attribute("dob", new Type(new Package("type", "datetime"), "Date")));
        final Struct struct = new Struct(new Type(new Package("test"), "User"), attrs);

        final List<JavaFile> files = transpiler.transpile(struct);
        expect(files.get(0), "Lk_struct_User.java", "testThreeAttr.concrete.java");
        expect(files.get(1), "User.java", "testThreeAttr.abstract.java");
    }

    @Test
    public void testWithFunctions() {

        final LinkedHashSet<Attribute> attrs = new LinkedHashSet<Attribute>();
        attrs.add(new Attribute("name", new Type(new Package("lorikeet", "core"), "Str")));
        final Struct struct = new Struct(new Type(new Package("test"), "User"), attrs);

        final Function func = new Function(
            new Type(new Package("test"), "User"),
            "bar",
            new LinkedHashSet<Attribute>(),
            new Type(new Package("lorikeet", "core"), "Str")
        );
        struct.addFunction(func);

        final List<JavaFile> files = transpiler.transpile(struct);
        expect(files.get(0), "Lk_struct_User.java", "testWithFunctions.concrete.java");
        expect(files.get(1), "User.java", "testWithFunctions.abstract.java");
    }

    void expect(JavaFile file, String fileName, String diskFile) {
        final String expected = read("lorikeet/transpile/StructTranspilerTest/" + diskFile);
        assertThat(trim(file.getContents())).isEqualTo(trim(expected));
        assertThat(file.getName()).isEqualTo(fileName);
    }

}
