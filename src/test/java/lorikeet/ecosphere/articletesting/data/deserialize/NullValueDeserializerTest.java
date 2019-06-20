package lorikeet.ecosphere.articletesting.data.deserialize;

import lorikeet.ecosphere.articletesting.data.NullValue;
import lorikeet.ecosphere.articletesting.reader.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NullValueDeserializerTest {

    private NullValueDeserializer deserializer = new NullValueDeserializer();

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