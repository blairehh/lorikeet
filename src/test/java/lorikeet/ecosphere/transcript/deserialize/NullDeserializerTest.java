package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.ecosphere.transcript.NullValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NullDeserializerTest {

    private NullDeserializer deserializer = new NullDeserializer();

    @Test
    public void testDeserializeNull() {
        assertThat(deserializer.deserialize("null").orPanic()).isEqualTo(new NullValue());
        assertThat(deserializer.deserialize("NULL").orPanic()).isEqualTo(new NullValue());
        assertThat(deserializer.deserialize("  null  ").orPanic()).isEqualTo(new NullValue());
    }

}