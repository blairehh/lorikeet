package lorikeet.lobe.articletesting.data.deserialize;

import lorikeet.lobe.articletesting.data.MapValue;
import lorikeet.lobe.articletesting.data.NullValue;
import lorikeet.lobe.articletesting.data.NumberValue;
import lorikeet.lobe.articletesting.data.StringValue;
import lorikeet.lobe.articletesting.reader.TextReader;
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