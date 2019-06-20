package lorikeet.ecosphere.articletesting.data.deserialize;

import lorikeet.ecosphere.articletesting.data.ListValue;
import lorikeet.ecosphere.articletesting.data.NumberValue;
import lorikeet.ecosphere.articletesting.data.ObjectValue;
import lorikeet.ecosphere.articletesting.data.StringValue;
import lorikeet.ecosphere.articletesting.reader.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectValueDeserializerTest {

    private final ObjectValueDeserializer deserializer = new ObjectValueDeserializer();

    @Test
    public void testEmptyObject() {
        TextReader reader = new TextReader("com.foo.Bar()", 0);
        ObjectValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getClassName()).isEqualTo("com.foo.Bar");
        assertThat(value.getData()).isEmpty();
        assertThat(reader.getCurrentIndex()).isEqualTo(13);
    }

    @Test
    public void testObjectWithOneField() {
        TextReader reader = new TextReader("com.foo.Bar(age: 11)", 0);
        ObjectValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getClassName()).isEqualTo("com.foo.Bar");
        assertThat(value.getData()).hasSize(1);
        assertThat(value.getData()).containsEntry("age", new NumberValue(11));
        assertThat(reader.getCurrentIndex()).isEqualTo(20);
    }

    @Test
    public void testObjectWithTwoFieldsField() {
        TextReader reader = new TextReader("com.foo.Bar(name: 'Jim', scores: [1, 2, 3])", 0);
        ObjectValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getClassName()).isEqualTo("com.foo.Bar");
        assertThat(value.getData()).hasSize(2);
        assertThat(value.getData()).containsEntry("name", new StringValue("Jim"));
        assertThat(value.getData()).containsEntry("scores", new ListValue(new NumberValue(1), new NumberValue(2), new NumberValue(3)));
        assertThat(reader.getCurrentIndex()).isEqualTo(43);
    }
}