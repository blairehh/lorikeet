package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.ecosphere.transcript.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class IdentifierValueDeserializerTest {

    private final IdentifierValueDeserializer deserializer = new IdentifierValueDeserializer();

    @Test
    public void testValid() {
        TextReader reader = new TextReader("foo", 0);
        assertThat(deserializer.deserialize(reader).orPanic().getIdentifier()).isEqualTo("foo");
        assertThat(reader.getCurrentIndex()).isEqualTo(3);
    }

    @Test
    public void testValidWithDot() {
        TextReader reader = new TextReader("foo.BAR", 0);
        assertThat(deserializer.deserialize(reader).orPanic().getIdentifier()).isEqualTo("foo.BAR");
        assertThat(reader.getCurrentIndex()).isEqualTo(7);
    }

    @Test
    public void testValidWithPadding() {
        TextReader reader = new TextReader("   foo.BA4R   ", 0);
        assertThat(deserializer.deserialize(reader).orPanic().getIdentifier()).isEqualTo("foo.BA4R");
        assertThat(reader.getCurrentIndex()).isEqualTo(14);
    }

}