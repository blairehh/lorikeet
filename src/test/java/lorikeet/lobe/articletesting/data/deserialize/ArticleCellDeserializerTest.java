package lorikeet.lobe.articletesting.data.deserialize;

import lorikeet.Dict;
import lorikeet.lobe.articletesting.data.BoolValue;
import lorikeet.lobe.articletesting.data.CellDefinition;
import lorikeet.lobe.articletesting.data.NumberValue;
import lorikeet.lobe.articletesting.data.StringValue;
import lorikeet.lobe.articletesting.reader.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleCellDeserializerTest {


    private final ArticleCellDeserializer deserializer = new ArticleCellDeserializer();

    @Test
    public void testParseNodeWithJustIdentifier() {
        TextReader reader = new TextReader("com.Foo()");
        CellDefinition cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Foo");
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(cell.getArguments().isEmpty()).isTrue();
        assertThat(reader.getCurrentIndex()).isEqualTo(9);
    }

    @Test
    public void testWithReturnValue() {
        TextReader reader = new TextReader("com.Foo() returns 34");
        CellDefinition cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Foo");
        assertThat(cell.getReturnValue().orPanic()).isEqualTo(new NumberValue(34));
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(cell.getArguments().isEmpty()).isTrue();
        assertThat(reader.getCurrentIndex()).isEqualTo(20);
    }

    @Test
    public void testWithExceptionThrown() {
        TextReader reader = new TextReader("com.Foo() throws java.lang.NullPointerException");
        CellDefinition cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Foo");
        assertThat(cell.getArguments().isEmpty()).isTrue();
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().orPanic()).isEqualTo("java.lang.NullPointerException");
        assertThat(reader.getCurrentIndex()).isEqualTo(47);
    }

    @Test
    public void testWithOneArgument() {
        TextReader reader = new TextReader("com.Baz('YES')");
        CellDefinition cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Baz");
        assertThat(cell.getArguments()).hasSize(1);
        assertThat(cell.getArguments()).isEqualTo(Dict.of("0", new StringValue("YES")));
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(reader.getCurrentIndex()).isEqualTo(14);
    }

    @Test
    public void testWithTwoArguments() {
        TextReader reader = new TextReader("com.Baz('YES', false)");
        CellDefinition cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Baz");
        assertThat(cell.getArguments()).hasSize(2);
        assertThat(cell.getArguments()).containsEntry("0", new StringValue("YES"));
        assertThat(cell.getArguments()).containsEntry("1", new BoolValue(false));
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(reader.getCurrentIndex()).isEqualTo(21);
    }

    @Test
    public void testWithTwoArgumentsWithReturn() {
        TextReader reader = new TextReader("com.Baz('YES', false)  returns  111");
        CellDefinition cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Baz");
        assertThat(cell.getArguments()).hasSize(2);
        assertThat(cell.getArguments()).containsEntry("0", new StringValue("YES"));
        assertThat(cell.getArguments()).containsEntry("1", new BoolValue(false));
        assertThat(cell.getReturnValue().orPanic()).isEqualTo(new NumberValue(111));
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(reader.getCurrentIndex()).isEqualTo(35);
    }

    @Test
    public void testInconsistentWhiteSpacing() {
        TextReader reader = new TextReader("    lorikeet.lobe.IssueDebitCard ('mastercard'  )\n");

        CellDefinition cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("lorikeet.lobe.IssueDebitCard");
        assertThat(cell.getArguments()).hasSize(1);
        assertThat(cell.getArguments()).containsEntry("0", new StringValue("mastercard"));
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(reader.getCurrentIndex()).isEqualTo(50);
    }


}