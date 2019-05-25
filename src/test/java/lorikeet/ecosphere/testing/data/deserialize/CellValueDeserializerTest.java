package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Dict;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.data.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CellValueDeserializerTest {

    private final CellValueDeserializer deserializer = new CellValueDeserializer();

    @Test
    public void testParseNodeWithJustIdentifier() {
        TextReader reader = new TextReader("<com.Foo  >", 1);
        CellValue cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Foo");
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(cell.getArguments().isEmpty()).isTrue();
        assertThat(reader.getCurrentIndex()).isEqualTo(11);
    }

    @Test
    public void testWithReturnValue() {
        TextReader reader = new TextReader("<com.Foo  -return=34>", 1);
        CellValue cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Foo");
        assertThat(cell.getReturnValue().orPanic()).isEqualTo(new NumberValue(34));
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(cell.getArguments().isEmpty()).isTrue();
        assertThat(reader.getCurrentIndex()).isEqualTo(21);
    }

    @Test
    public void testWithExceptionThrown() {
        TextReader reader = new TextReader("<com.Foo  -exception=java.lang.NullPointerException>", 1);
        CellValue cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Foo");
        assertThat(cell.getArguments().isEmpty()).isTrue();
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().orPanic()).isEqualTo(new IdentifierValue("java.lang.NullPointerException"));
        assertThat(reader.getCurrentIndex()).isEqualTo(52);
    }

    @Test
    public void testWithOneArgument() {
        TextReader reader = new TextReader("<com.Baz bar='YES' >", 1);
        CellValue cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Baz");
        assertThat(cell.getArguments()).isEqualTo(Dict.of("bar", new StringValue("YES")));
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(reader.getCurrentIndex()).isEqualTo(20);
    }

    @Test
    public void testWithTwoArguments() {
        TextReader reader = new TextReader("<com.Baz bar='YES' foo=false>", 1);
        CellValue cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Baz");
        assertThat(cell.getArguments()).hasSize(2);
        assertThat(cell.getArguments()).containsEntry("bar", new StringValue("YES"));
        assertThat(cell.getArguments()).containsEntry("foo", new BoolValue(false));
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(reader.getCurrentIndex()).isEqualTo(29);
    }


}