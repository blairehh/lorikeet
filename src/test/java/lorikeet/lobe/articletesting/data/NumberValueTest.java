package lorikeet.lobe.articletesting.data;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberValueTest {

    @Test
    public void testIsEqualWithSelf() {
        assertThat(new NumberValue(1).equality(new NumberValue(1))).isEqualTo(Equality.EQUAL);
        assertThat(new NumberValue(1).equality(new NumberValue(1.0))).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testEqualityNotIfDifferentNumbers() {
        assertThat(new NumberValue(1).equality(new NumberValue(3))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotEqualIfDifferentType() {
        assertThat(new NumberValue(1).equality(new StringValue("1"))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testEqualityIsUnknownWithSymbolic() {
        assertThat(new NumberValue(1).equality(new AnyValue())).isEqualTo(Equality.UNKNOWN);
    }
}