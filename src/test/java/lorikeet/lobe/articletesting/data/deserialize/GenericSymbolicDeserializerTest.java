package lorikeet.lobe.articletesting.data.deserialize;

import lorikeet.lobe.articletesting.data.BoolValue;
import lorikeet.lobe.articletesting.data.GenericSymbolicValue;
import lorikeet.lobe.articletesting.data.NumberValue;
import lorikeet.lobe.articletesting.data.StringValue;
import lorikeet.lobe.articletesting.reader.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class GenericSymbolicDeserializerTest {

    private final GenericSymbolicDeserializer deserializer = new GenericSymbolicDeserializer();

    @Test
    public void testSymbolicValueWithNoArguments() {
        TextReader reader = new TextReader("@foo()", 0);
        GenericSymbolicValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getName()).isEqualTo("foo");
        assertThat(value.getArguments()).isEmpty();
        assertThat(value.isSymbolic()).isTrue();
        assertThat(reader.getCurrentIndex()).isEqualTo(6);
    }

    @Test
    public void testSymbolicValueWithNoArgumentsAndNoParenthesis() {
        TextReader reader = new TextReader("@foo", 0);
        GenericSymbolicValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getName()).isEqualTo("foo");
        assertThat(value.isSymbolic()).isTrue();
        assertThat(value.getArguments()).isEmpty();
        assertThat(reader.getCurrentIndex()).isEqualTo(4);
    }

    @Test
    public void testSymbolicValueWithNoArgumentsAndNoParenthesisFollowedByRandomSymbol() {
        TextReader reader = new TextReader("@foo,", 0);
        GenericSymbolicValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getName()).isEqualTo("foo");
        assertThat(value.isSymbolic()).isTrue();
        assertThat(value.getArguments()).isEmpty();
        assertThat(reader.getCurrentIndex()).isEqualTo(4);
    }

    @Test
    public void testWithOneArgument() {
        TextReader reader = new TextReader("@foo('bar')", 0);
        GenericSymbolicValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getName()).isEqualTo("foo");
        assertThat(value.isSymbolic()).isTrue();
        assertThat(value.getArguments()).containsExactly(new StringValue("bar"));
        assertThat(reader.getCurrentIndex()).isEqualTo(11);
    }

    @Test
    public void testWithTwoArguments() {
        TextReader reader = new TextReader("@foo('bar', -6767.434)", 0);
        GenericSymbolicValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getName()).isEqualTo("foo");
        assertThat(value.isSymbolic()).isTrue();
        assertThat(value.getArguments()).containsExactly(new StringValue("bar"), new NumberValue(-6767.434));
        assertThat(reader.getCurrentIndex()).isEqualTo(22);
    }

    @Test
    public void testWithThreeArgumentsWithWeirdSpacing() {
        TextReader reader = new TextReader( "  @foo  (   'bar' ,   -6767.434  ,true  ) ", 0);
        GenericSymbolicValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value.getName()).isEqualTo("foo");
        assertThat(value.isSymbolic()).isTrue();
        assertThat(value.getArguments()).containsExactly(new StringValue("bar"), new NumberValue(-6767.434), new BoolValue(true));
        assertThat(reader.getCurrentIndex()).isEqualTo(42);
    }

}