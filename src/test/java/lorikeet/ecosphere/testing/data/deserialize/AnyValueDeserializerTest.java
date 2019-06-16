package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.AnyValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.AnyValueDoesNotAcceptArguments;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AnyValueDeserializerTest {

    private final AnyValueDeserializer deserializer = new AnyValueDeserializer();

    @Test
    public void test() {
        TextReader reader = new TextReader("@any");
        AnyValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value).isEqualTo(new AnyValue());
    }

    @Test
    public void testCanNotHaveArguments() {
        TextReader reader = new TextReader("@any(true)");
        Err<AnyValue> value = deserializer.deserialize(reader);
        assertThat(value.failedWith(new AnyValueDoesNotAcceptArguments())).isTrue();
    }

}