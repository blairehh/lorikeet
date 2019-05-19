package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.ecosphere.transcript.NullValue;
import lorikeet.ecosphere.transcript.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NullDeserializerTest {

    private NullDeserializer deserializer = new NullDeserializer();

    @Test
    public void testDeserializeNull() {
        assertThat(deserializer.deserialize(new TextReader("null", 0)).orPanic()).isEqualTo(new NullValue());
        assertThat(deserializer.deserialize(new TextReader("null:", 0)).orPanic()).isEqualTo(new NullValue());
        assertThat(deserializer.deserialize(new TextReader("NULL", 0)).orPanic()).isEqualTo(new NullValue());
    }

    @Test
    public void testJumpsWhiteSpace() {
        TextReader reader = new TextReader("  null  ", 0);
        assertThat(deserializer.deserialize(reader).orPanic()).isEqualTo(new NullValue());
        assertThat(reader.getCurrentIndex()).isEqualTo(8);
    }

}