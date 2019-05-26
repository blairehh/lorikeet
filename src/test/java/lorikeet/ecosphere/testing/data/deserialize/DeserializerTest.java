package lorikeet.ecosphere.testing.data.deserialize;


import lorikeet.Dict;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.HashValue;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.ObjectValue;
import lorikeet.ecosphere.testing.reader.TextReader;
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
    public void testHashValue() {
        TextReader reader = new TextReader(" com.app.Foo#34", 0);
        HashValue result = (HashValue) new Deserializer().deserialize(reader).orPanic();
        assertThat(result).isEqualTo(new HashValue("com.app.Foo", "34"));
    }

    @Test
    public void testObject() {
        TextReader reader = new TextReader("  com.app.Foo(limit: 1) ", 0);
        ObjectValue result = (ObjectValue)new Deserializer().deserialize(reader).orPanic();
        assertThat(result).isEqualTo(new ObjectValue("com.app.Foo", Dict.of("limit", new NumberValue(1))));
        assertThat(reader.getCurrentIndex()).isEqualTo(24);
    }

    @Test
    public void testIdentifier() {
        TextReader reader = new TextReader("BAR", 0);
        IdentifierValue result = (IdentifierValue)new Deserializer().deserialize(reader).orPanic();
        assertThat(result).isEqualTo(new IdentifierValue("BAR"));
        assertThat(reader.getCurrentIndex()).isEqualTo(3);
    }
}