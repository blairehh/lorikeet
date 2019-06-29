package lorikeet.lobe.articletesting.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringValueTest {

    @Test
    public void testEqualityWithSomeString() {
        assertThat(new StringValue("hello").equality(new StringValue("hello"))).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testNotEqualWithDifferentString() {
        assertThat(new StringValue("hello").equality(new StringValue("world"))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotEqualIfDifferentType() {
        assertThat(new StringValue("hello").equality(new BoolValue(true))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testEqualityIsUnknownIfSymbolic() {
        assertThat(new StringValue("hello").equality(new AnyValue())).isEqualTo(Equality.UNKNOWN);
    }

}