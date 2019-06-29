package lorikeet.lobe.articletesting.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashValueTest {

    @Test
    public void testIsEqual() {
        assertThat(hashValue("foo", "abcde").equality(hashValue("foo", "abcde")))
            .isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testIsNotEqual() {
        assertThat(hashValue("foo", "abcde").equality(hashValue("foo", "bar")))
            .isEqualTo(Equality.NOT_EQUAL);

        assertThat(hashValue("bar", "abcde").equality(hashValue("foo", "abcde")))
            .isEqualTo(Equality.NOT_EQUAL);

        assertThat(hashValue("bar", "foo").equality(hashValue("baz", "doh")))
            .isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testEqualityIsUnknownForSymbolic() {
        assertThat(hashValue("a", "b").equality(new AnyValue())).isEqualTo(Equality.UNKNOWN);
    }

    private static HashValue hashValue(String className, String hashValue) {
        return new HashValue(className, hashValue);
    }
}