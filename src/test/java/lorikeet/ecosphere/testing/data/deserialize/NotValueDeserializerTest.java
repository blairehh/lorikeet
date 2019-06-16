package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.ecosphere.testing.data.AnyValue;
import lorikeet.ecosphere.testing.data.NotValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.NotValueMustBeNamedNot;
import lorikeet.error.NotValueMustBeSuppliedWithArguments;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NotValueDeserializerTest {

    private final NotValueDeserializer deserializer = new NotValueDeserializer();

    @Test
    public void testWithWrongName() {
        TextReader reader = new TextReader("@noott");
        assertThat(deserializer.deserialize(reader).failedWith(new NotValueMustBeNamedNot())).isTrue();
    }

    @Test
    public void testNotValueMustHaveArguments() {
        TextReader reader = new TextReader("@not()");
        assertThat(deserializer.deserialize(reader).failedWith(new NotValueMustBeSuppliedWithArguments())).isTrue();
    }

    @Test
    public void testWithOneNotValue() {
        TextReader reader = new TextReader("@not(1)");
        NotValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getValues()).containsOnly(new NumberValue(1));
    }

    @Test
    public void testWithTwoNotValues() {
        TextReader reader = new TextReader("@not(1, '1')");
        NotValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getValues()).containsOnly(new NumberValue(1), new StringValue("1"));
    }

    @Test
    public void testWithNotSymbolicValue() {
        TextReader reader = new TextReader("@not(@any)");
        NotValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getValues()).containsOnly(new AnyValue());
    }
}