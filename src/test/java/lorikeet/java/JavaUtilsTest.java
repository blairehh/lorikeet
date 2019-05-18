package lorikeet.java;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaUtilsTest {

    @Test
    public void testValidIdentifier() {
        assertThat(JavaUtils.isValidIdentifier("lorikeet.Seq")).isTrue();
    }

    @Test
    public void testValidIdentifierJustClassName() {
        assertThat(JavaUtils.isValidIdentifier("FooBar")).isTrue();
    }

    @Test
    public void testValidIdentifierMultiplePackages() {
        assertThat(JavaUtils.isValidIdentifier("com.foo.bar.Baz")).isTrue();
    }

    @Test
    public void testValidIdentifierCannotStartWithNumber() {
        assertThat(JavaUtils.isValidIdentifier("com.2D")).isFalse();
    }

    @Test
    public void testValidIdentifierCannotHaveDoubleDot() {
        assertThat(JavaUtils.isValidIdentifier("com..Foo")).isFalse();
    }

    @Test
    public void testValidIdentifierCannotStartWithDot() {
        assertThat(JavaUtils.isValidIdentifier(".Foo")).isFalse();
    }

    @Test
    public void testValidIdentifierCannotEndWithDot() {
        assertThat(JavaUtils.isValidIdentifier("com.Foo.")).isFalse();
    }


    @Test
    public void testValidIdentifierCanHaveUnderscore() {
        assertThat(JavaUtils.isValidIdentifier("com.Foo_Bar")).isTrue();
    }

}