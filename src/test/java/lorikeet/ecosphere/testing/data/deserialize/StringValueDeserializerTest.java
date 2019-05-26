package lorikeet.ecosphere.testing.data.deserialize;


import lorikeet.ecosphere.testing.reader.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringValueDeserializerTest {

    private final StringValueDeserializer deserializer = new StringValueDeserializer();

    @Test
    public void testString() {
        TextReader reader = new TextReader("'abc defg'", 0);
        assertThat(deserializer.deserialize(reader).orPanic().getValue()).isEqualTo("abc defg");
    }

    @Test
    public void testFailsIfNoQuote() {
        TextReader reader = new TextReader("true false", 0);
        assertThat(deserializer.deserialize(reader).isPresent()).isFalse();
    }
}