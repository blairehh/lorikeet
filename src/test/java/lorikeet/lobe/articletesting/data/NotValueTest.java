package lorikeet.lobe.articletesting.data;

import lorikeet.Seq;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NotValueTest {

    @Test
    public void testNoMatchingValuesSoIsEqual() {
        NotValue notValue = new NotValue(Seq.of(new NumberValue(1), new NumberValue(2)));
        assertThat(notValue.equality(new NumberValue(3))).isEqualTo(Equality.EQUAL);
    }

    @Test
    public void testMatchingValuesSoIsNotEqual() {
        NotValue notValue = new NotValue(Seq.of(new NumberValue(1), new NumberValue(2)));
        assertThat(notValue.equality(new NumberValue(2))).isEqualTo(Equality.NOT_EQUAL);
    }

    @Test
    public void testNotWithAnyIsAlwaysNotEquals() {
        NotValue notValue = new NotValue(Seq.of(new AnyValue()));
        assertThat(notValue.equality(new NumberValue(2))).isEqualTo(Equality.NOT_EQUAL);
    }
}