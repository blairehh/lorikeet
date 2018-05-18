package lorikeet.parse;

import lorikeet.lang.Package;
import lorikeet.lang.Type;
import lorikeet.lang.Use;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeTableTest {

    @Test
    public void testDefaultImported() {
        TypeTable table = new TypeTable();
        assertThat(table.get("Str").get().getType())
            .isEqualTo(new Type(new Package("lorikeet", "core"), "Str"));
    }

    @Test
    public void testWithAddedUse() {
        TypeTable table = new TypeTable();
        table.add(new Use(new Package("com", "foo"), "Bar", "Bar"));
        assertThat(table.get("Bar").get().getType())
            .isEqualTo(new Type(new Package("com", "foo"), "Bar"));
    }

    @Test
    public void testWithAddedUseAliased() {
        TypeTable table = new TypeTable();
        table.add(new Use(new Package("com", "foo"), "Bar", "Baz"));
        assertThat(table.get("Baz").get().getType())
            .isEqualTo(new Type(new Package("com", "foo"), "Bar"));
    }

    @Test
    public void testWithAddedType() {
        TypeTable table = new TypeTable();
        table.add(new Type(new Package("com", "foo"), "Bar"));
        assertThat(table.get("Bar").get().getType())
            .isEqualTo(new Type(new Package("com", "foo"), "Bar"));
    }

    @Test
    public void testNotFound() {
        TypeTable table = new TypeTable();
        assertThat(table.get("FooBar").isPresent()).isFalse();
    }
}
