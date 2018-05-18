package lorikeet.parse;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PatternsTest {

    @Test
    public void testValidTypeName() {
        assertThat(Patterns.isTypeName("Animal")).isTrue();
    }

    @Test
    public void testTypeNameCantStartWithLowerOrNumber() {
        assertThat(Patterns.isTypeName("animal")).isFalse();
        assertThat(Patterns.isTypeName("7animal")).isFalse();
    }

    @Test
    public void testTypeNameCanContainNumber() {
        assertThat(Patterns.isTypeName("Foo1")).isTrue();
    }

    @Test
    public void testTypeNameContNotContainUnderscore() {
        assertThat(Patterns.isTypeName("Foo_baar")).isFalse();
    }

    @Test
    public void testTypeNameContNotContainDot() {
        assertThat(Patterns.isTypeName("Foo.baar")).isFalse();
    }


    @Test
    public void testValidPackageeName() {
        assertThat(Patterns.isPackageName("com")).isTrue();
    }

    @Test
    public void testPackageNameCantStartWithUpperOrNumber() {
        assertThat(Patterns.isPackageName("Com")).isFalse();
        assertThat(Patterns.isPackageName("9om")).isFalse();
    }

    @Test
    public void testPakageNameCanContainNumber() {
        assertThat(Patterns.isPackageName("foo1")).isTrue();
    }

    @Test
    public void testPackageNameContNotContainUnderscore() {
        assertThat(Patterns.isPackageName("foo_bar")).isFalse();
    }

    @Test
    public void testPackageNameContNotContainDot() {
        assertThat(Patterns.isPackageName("foo.baar")).isFalse();
    }


}
