package lorikeet.ecosphere.transcript.deserialize;


import lorikeet.Dict;
import lorikeet.ecosphere.transcript.BoolValue;
import lorikeet.ecosphere.transcript.NumberValue;
import lorikeet.ecosphere.transcript.ObjectValue;
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

    @Test
    public void testObject() {
        TextReader reader = new TextReader("  com.app.Foo(limit: 1) ", 0);
        ObjectValue result = (ObjectValue)new Deserializer().deserialize(reader).orPanic();
        assertThat(result).isEqualTo(new ObjectValue("com.app.Foo", Dict.of("limit", new NumberValue(1))));
        assertThat(reader.getCurrentIndex()).isEqualTo(24);
    }
}