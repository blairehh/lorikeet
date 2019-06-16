package lorikeet.ecosphere.testing.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NullValueTest {

    @Test
    public void testIsEqualWithItself() {
        assertThat(new NullValue().equality(new NullValue())).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testNotEqualWithOtherType() {
        assertThat(new NullValue().equality(new StringValue("oi"))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testEqualityIsUnknownIfSymbolic() {
        assertThat(new NullValue().equality(new AnyValue())).isEqualTo(Equality.UNKNOWN);
    }

}