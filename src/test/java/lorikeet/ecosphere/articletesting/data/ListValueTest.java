package lorikeet.ecosphere.articletesting.data;

import lorikeet.Seq;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListValueTest {

    @Test
    public void testEqualityWithSameItems() {
        assertThat(list().equality(list())).isEqualTo(Equality.EQUAL);
        assertThat(list(new NumberValue(2)).equality(list(new NumberValue(2)))).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testListWithDifferentSizesIsNotEqual() {
        assertThat(list(new NumberValue(1)).equality(list(new NumberValue(1), new NumberValue(2)))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotEqualDueToItemsDiffer() {
        assertThat(list(new NumberValue(2)).equality(list(new NumberValue(3)))).isEqualTo(Equality.NOT_EQUAL);
        assertThat(list(new StringValue("3")).equality(list(new NumberValue(3)))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testIsNotEqualIfDifferentType() {
        assertThat(list(new BoolValue(true)).equality(new BoolValue(true))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testEqualityIsUnknownWithSymbolic() {
        assertThat(list().equality(new AnyValue())).isEqualTo(Equality.UNKNOWN);
    }

    @Test
    public void testEqualityIsEqualForNestedAnyValue() {
        assertThat(list(new BoolValue(true)).equality(list(new AnyValue()))).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testEqualityIsEqualForNestedAnyValueWithMatchingValues() {
        assertThat(list(new BoolValue(false), new BoolValue(true)).equality(list(new BoolValue(false), new AnyValue()))).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testNotEqualOutOfOrder() {
        assertThat(list(new NumberValue(2), new NumberValue(1)).equality(list(new NumberValue(1), new NumberValue(2)))).isEqualTo(Equality.NOT_EQUAL);
    }
    private static <A extends Value> ListValue list(A... items) {
        return new ListValue(Seq.of(items));
    }
}