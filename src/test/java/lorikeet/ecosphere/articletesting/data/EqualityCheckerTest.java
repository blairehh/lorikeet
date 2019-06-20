package lorikeet.ecosphere.articletesting.data;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualityCheckerTest {

    private final EqualityChecker equality = new EqualityChecker();

    @Test
    public void testTwoOfSameTypeAndValueIsEqual() {
        assertThat(equality.checkEquality(new StringValue("oi"), new StringValue("oi"))).isTrue();
    }

    @Test
    public void testTwoOfSameTypeButDifferentValuesAreNotEqual() {
        assertThat(equality.checkEquality(new StringValue("oi"), new StringValue("oi!"))).isFalse();
    }

    @Test
    public void testTwoOfDifferentTypesAreNotEqual() {
        assertThat(equality.checkEquality(new StringValue("oi"), new NumberValue(0))).isFalse();
    }

    @Test
    public void testWithAnyIsEqual() {
        assertThat(equality.checkEquality(new StringValue("oi"), new AnyValue())).isTrue();
        assertThat(equality.checkEquality(new AnyValue(), new StringValue("oi"))).isTrue();
    }
}