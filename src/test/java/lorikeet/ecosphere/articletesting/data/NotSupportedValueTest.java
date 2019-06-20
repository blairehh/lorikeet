package lorikeet.ecosphere.articletesting.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NotSupportedValueTest {

    @Test
    public void testEqualityWithItself() {
        assertThat(new NotSupportedValue().equality(new NotSupportedValue())).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testNotEqualWithOtherTypes() {
        assertThat(new NotSupportedValue().equality(new StringValue("foo"))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testEqualityIsUnknownWithSymbolic() {
        assertThat(new NotSupportedValue().equality(new AnyValue())).isEqualTo(Equality.UNKNOWN);
    }
}