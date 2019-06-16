package lorikeet.ecosphere.testing.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnyValueTest {

    @Test
    public void testEqualityWithSelf() {
        assertThat(new AnyValue().equality(new AnyValue())).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testEqualityWithOtherValues() {
        assertThat(new AnyValue().equality(new BoolValue(false))).isEqualTo(Equality.EQUAL);
        assertThat(new AnyValue().equality(new StringValue("oi oi oi"))).isEqualTo(Equality.EQUAL);
    }

}