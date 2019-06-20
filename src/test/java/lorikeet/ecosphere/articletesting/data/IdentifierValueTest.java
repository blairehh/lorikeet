package lorikeet.ecosphere.articletesting.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentifierValueTest {

    @Test
    public void testIsEqual() {
        assertThat(new IdentifierValue("com.foo").equality(new IdentifierValue("com.foo"))).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testIsNotEqual() {
        assertThat(new IdentifierValue("com.foo").equality(new IdentifierValue("foo.com"))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testIsNotEqualDueToDifferentType() {
        assertThat(new IdentifierValue("com.foo").equality(new StringValue("com.foo"))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testEqualityIsUnknownForSymbolic() {
        assertThat(new IdentifierValue("foo").equality(new AnyValue())).isEqualTo(Equality.UNKNOWN);
    }

}