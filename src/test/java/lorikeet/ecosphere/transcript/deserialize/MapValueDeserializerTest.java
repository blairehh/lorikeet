package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.ecosphere.transcript.MapValue;
import lorikeet.ecosphere.transcript.NullValue;
import lorikeet.ecosphere.transcript.NumberValue;
import lorikeet.ecosphere.transcript.StringValue;
import lorikeet.ecosphere.transcript.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapValueDeserializerTest {

    private final MapValueDeserializer deserializer = new MapValueDeserializer();

    @Test
    public void testEmptyMap() {
        TextReader reader = new TextReader("{}", 0);
        MapValue map = deserializer.deserialize(reader).orPanic();
        assertThat(map.getData()).hasSize(0);
        assertThat(reader.getCurrentIndex()).isEqualTo(2);
    }

    @Test
    public void testSingleEntry() {
        TextReader reader = new TextReader("{'abc': 'def'}", 0);
        MapValue map = deserializer.deserialize(reader).orPanic();
        assertThat(map.getData()).hasSize(1);
        assertThat(map.getData()).containsEntry(new StringValue("abc"), new StringValue("def"));
        assertThat(reader.getCurrentIndex()).isEqualTo(14);
    }

    @Test
    public void testMultiEntry() {
        TextReader reader = new TextReader("{'abc': 'def', 123: 456}", 0);
        MapValue map = deserializer.deserialize(reader).orPanic();
        assertThat(map.getData()).hasSize(2);
        assertThat(map.getData()).containsEntry(new StringValue("abc"), new StringValue("def"));
        assertThat(map.getData()).containsEntry(new NumberValue(123), new NumberValue(456));
        assertThat(reader.getCurrentIndex()).isEqualTo(24);
    }

    @Test
    public void testNullAsKey() {
        TextReader reader = new TextReader("{'abc': 'def', 123: 456 , null: null }", 0);
        MapValue map = deserializer.deserialize(reader).orPanic();
        assertThat(map.getData()).hasSize(3);
        assertThat(map.getData()).containsEntry(new StringValue("abc"), new StringValue("def"));
        assertThat(map.getData()).containsEntry(new NumberValue(123), new NumberValue(456));
        assertThat(map.getData()).containsEntry(new NullValue(), new NullValue());
        assertThat(reader.getCurrentIndex()).isEqualTo(38);
    }

}