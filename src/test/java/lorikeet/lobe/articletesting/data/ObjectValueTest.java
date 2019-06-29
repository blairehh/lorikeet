package lorikeet.lobe.articletesting.data;

import lorikeet.Dict;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectValueTest {

    @Test
    public void testIsEqualWithSelfWithSameData() {
        final ObjectValue a = new ObjectValue("foo", Dict.of("bar", new NumberValue(1)));
        final ObjectValue b = new ObjectValue("foo", Dict.of("bar", new NumberValue(1)));

        assertThat(a.equality(b)).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testNotEqualWithDifferentData() {
        final ObjectValue a = new ObjectValue("foo", Dict.of("bar", new NumberValue(1)));
        final ObjectValue b = new ObjectValue("foo", Dict.of("baz", new NumberValue(1)));

        assertThat(a.equality(b)).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotEqualWithDifferentDataType() {
        final ObjectValue a = new ObjectValue("foo", Dict.of("bar", new NumberValue(1)));
        final ObjectValue b = new ObjectValue("foo", Dict.of("bar", new StringValue("1")));

        assertThat(a.equality(b)).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotEqualWithDifferentAmountsData() {
        final ObjectValue a = new ObjectValue("foo", Dict.of("bar", new NumberValue(1)));
        final ObjectValue b = new ObjectValue("foo", Dict.of("bar", new NumberValue(1), "b", new NumberValue(2)));

        assertThat(a.equality(b)).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotEqualWithDifferentClass() {
        final ObjectValue a = new ObjectValue("foo", Dict.of("bar", new NumberValue(1)));
        final ObjectValue b = new ObjectValue("doh", Dict.of("bar", new NumberValue(1)));

        assertThat(a.equality(b)).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotEqualWithDifferentType() {
        final ObjectValue a = new ObjectValue("foo", Dict.of("bar", new NumberValue(1)));

        assertThat(a.equality(new StringValue("bar"))).isEqualTo(Equality.NOT_EQUAL);
    }
    @Test
    public void testEqualityIsUnknownIfSymbolic() {
        final ObjectValue a = new ObjectValue("foo", Dict.of("bar", new NumberValue(1)));

        assertThat(a.equality(new AnyValue())).isEqualTo(Equality.UNKNOWN);
    }

    @Test
    public void testEqualityIsEqualIfDataIsSymbolic() {
        final ObjectValue a = new ObjectValue("foo", Dict.of("bar", new NumberValue(1)));
        final ObjectValue b = new ObjectValue("foo", Dict.of("bar", new AnyValue()));

        assertThat(a.equality(b)).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testEqualityWithDeeplyNestedAny() {
        final ObjectValue a = new ObjectValue("foo", Dict.of("bar", new ObjectValue("bar", Dict.of("a", new StringValue("a")))));
        final ObjectValue b = new ObjectValue("foo", Dict.of("bar", new ObjectValue("bar", Dict.of("a", new AnyValue()))));

        assertThat(a.equality(b)).isEqualTo(Equality.EQUAL);
    }
}