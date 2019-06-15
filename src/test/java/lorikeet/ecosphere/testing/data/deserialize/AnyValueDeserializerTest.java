package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.ecosphere.testing.data.AnyValue;
import lorikeet.ecosphere.testing.reader.TextReader;
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

}