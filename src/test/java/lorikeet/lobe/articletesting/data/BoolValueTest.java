package lorikeet.lobe.articletesting.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoolValueTest {

    @Test
    public void testIsEqualIfSameBooleanValue() {
        assertThat(new BoolValue(true).equality(new BoolValue(true))).isEqualTo(Equality.EQUAL);
        assertThat(new BoolValue(false).equality(new BoolValue(false))).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testIsNotEqualIfDifferentValues() {
        assertThat(new BoolValue(true).equality(new BoolValue(false))).isEqualTo(Equality.NOT_EQUAL);
        assertThat(new BoolValue(false).equality(new BoolValue(true))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testIsUnknownWithSymbolicValue() {
        assertThat(new BoolValue(true).equality(new AnyValue())).isEqualTo(Equality.UNKNOWN);
    }
}