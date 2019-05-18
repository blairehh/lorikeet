package lorikeet.ecosphere.transcript.deserialize;


import lorikeet.ecosphere.transcript.BoolValue;
import lorikeet.ecosphere.transcript.NumberValue;
import lorikeet.ecosphere.transcript.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeserializerTest {


    @Test
    public void testDeserializeBoolean() {
        TextReader reader = new TextReader("true false", 0);
        assertThat(new Deserializer().deserialize(reader).orPanic()).isEqualTo(new BoolValue(true));
    }

    @Test
    public void testDeserializeNumber() {
        TextReader reader = new TextReader("45", 0);
        assertThat(new Deserializer().deserialize(reader).orPanic()).isEqualTo(new NumberValue(45));
    }
}