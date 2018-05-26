package lorikeet.compile;

import lorikeet.lang.LorikeetType;
import lorikeet.lang.Package;
import lorikeet.lang.Module;
import lorikeet.lang.Struct;
import lorikeet.lang.SourceFile;
import lorikeet.lang.Type;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeRegistryTest {


    private static Package pkg = new Package("test");

    @Test
    public void testAdd() {
        TypeRegistry registry = new TypeRegistry();
        final Struct struct1 = new Struct(new Type(pkg, "User"), Collections.emptySet());
        final Struct struct2 = new Struct(new Type(pkg, "Role"), Collections.emptySet());
        final Module module = new Module(new Type(pkg, "Database"), Collections.emptySet());

        final SourceFile sourceFile = new SourceFile(
            pkg,
            Arrays.asList(struct1, struct2),
            Arrays.asList(module)
        );

        assertThat(registry.enter(sourceFile).isPresent()).isFalse();

        assertThat(registry.get(struct1.getType()).get()).isEqualTo(struct1);
        assertThat(registry.get(struct2.getType()).get()).isEqualTo(struct2);
        assertThat(registry.get(module.getType()).get()).isEqualTo(module);
    }

    @Test
    public void testFailsDuplicate() {
        TypeRegistry registry = new TypeRegistry();
        final Struct struct1 = new Struct(new Type(pkg, "User"), Collections.emptySet());
        final Struct struct2 = new Struct(new Type(pkg, "Role"), Collections.emptySet());
        final Module module = new Module(new Type(pkg, "Role"), Collections.emptySet());

        final SourceFile sourceFile = new SourceFile(
            pkg,
            Arrays.asList(struct1, struct2),
            Arrays.asList(module)
        );

        assertThat(registry.enter(sourceFile).isPresent()).isTrue();

        assertThat(registry.get(struct1.getType()).get()).isEqualTo(struct1);
    }

}
