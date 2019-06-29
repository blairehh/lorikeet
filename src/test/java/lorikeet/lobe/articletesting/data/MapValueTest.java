package lorikeet.lobe.articletesting.data;

import lorikeet.Dict;
import lorikeet.Seq;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MapValueTest {

    @Test
    public void testNotEqualWithOtherType() {
        assertThat(new MapValue(Dict.empty()).equality(new StringValue("foo"))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotEqualIfDataDiffers() {
        Equality equality = equality(
            Dict.of(new StringValue("a"), new StringValue("a")),
            Dict.of(new StringValue("a"), new StringValue("b"))
        );

        assertThat(equality).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotEqualIfDataSizeDiffers() {
        Equality equality = equality(
            Dict.of(new StringValue("a"), new StringValue("a")),
            Dict.of(new StringValue("a"), new StringValue("a"), new StringValue("b"), new StringValue("b"))
        );

        assertThat(equality).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testIsEqual() {
        Equality equality = equality(
            Dict.of(new StringValue("a"), new StringValue("a")),
            Dict.of(new StringValue("a"), new StringValue("a"))
        );

        assertThat(equality).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testIsUnknownWithSymbolicValue() {
        assertThat(new MapValue(Dict.empty()).equality(new GenericSymbolicValue("foo", Seq.empty()))).isEqualTo(Equality.UNKNOWN);
    }

    @Test
    public void testIsEqualIfValueIsAny() {
        Equality equality = equality(
            Dict.of(new StringValue("a"), new StringValue("a")),
            Dict.of(new StringValue("a"), new AnyValue())
        );

        assertThat(equality).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testAnyKeyIsEqual() {
        final MapValue a = new MapValue(Dict.of(new StringValue("a"), new StringValue("a")));
        final MapValue b = new MapValue(Dict.of(new AnyValue(), new StringValue("a")));

        assertThat(a.equality(b)).isEqualTo(Equality.EQUAL);
        assertThat(b.equality(a)).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testAnyKeyAndValueIsEqualToMultipleKeysAndValues() {
        final MapValue a = new MapValue(Dict.of(new StringValue("a"), new StringValue("a"), new StringValue("b"), new StringValue("b")));
        final MapValue b = new MapValue(Dict.of(new AnyValue(), new AnyValue()));

        assertThat(a.equality(b)).isEqualTo(Equality.EQUAL);
        assertThat(b.equality(a)).isEqualTo(Equality.EQUAL);
    }

    private static Equality equality(Dict<Value, Value> a, Dict<Value, Value> b) {
        return new MapValue(a).equality(new MapValue(b));
    }
}