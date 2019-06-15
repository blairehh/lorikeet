package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Dict;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleCellDeserializerTest {


    private final ArticleCellDeserializer deserializer = new ArticleCellDeserializer();

    @Test
    public void testParseNodeWithJustIdentifier() {
        TextReader reader = new TextReader("com.Foo()");
        CellValue cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Foo");
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(cell.getArguments().isEmpty()).isTrue();
        assertThat(reader.getCurrentIndex()).isEqualTo(9);
    }

//    @Test
//    public void testWithReturnValue() {
//        TextReader reader = new TextReader("com.Foo(-return=34)");
//        CellValue cell = deserializer.deserialize(reader).orPanic();
//        assertThat(cell.getClassName()).isEqualTo("com.Foo");
//        assertThat(cell.getReturnValue().orPanic()).isEqualTo(new NumberValue(34));
//        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
//        assertThat(cell.getArguments().isEmpty()).isTrue();
//        assertThat(reader.getCurrentIndex()).isEqualTo(21);
//    }

//    @Test
//    public void testWithExceptionThrown() {
//        TextReader reader = new TextReader("com.Foo(-exception=java.lang.NullPointerException)");
//        CellValue cell = deserializer.deserialize(reader).orPanic();
//        assertThat(cell.getClassName()).isEqualTo("com.Foo");
//        assertThat(cell.getArguments().isEmpty()).isTrue();
//        assertThat(cell.getReturnValue().isPresent()).isFalse();
//        assertThat(cell.getExceptionThrown().orPanic()).isEqualTo("java.lang.NullPointerException");
//        assertThat(reader.getCurrentIndex()).isEqualTo(52);
//    }

    @Test
    public void testWithOneArgument() {
        TextReader reader = new TextReader("com.Baz('YES')");
        CellValue cell = deserializer.deserialize(reader).orPanic();
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
        CellValue cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("com.Baz");
        assertThat(cell.getArguments()).hasSize(2);
        assertThat(cell.getArguments()).containsEntry("0", new StringValue("YES"));
        assertThat(cell.getArguments()).containsEntry("1", new BoolValue(false));
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(cell.getExceptionThrown().isPresent()).isFalse();
        assertThat(reader.getCurrentIndex()).isEqualTo(21);
    }

    @Test
    public void testInconsistentWhiteSpacing() {
        TextReader reader = new TextReader("    lorikeet.ecosphere.IssueDebitCard ('mastercard'  )\n");

        CellValue cell = deserializer.deserialize(reader).orPanic();
        assertThat(cell.getClassName()).isEqualTo("lorikeet.ecosphere.IssueDebitCard");
        assertThat(cell.getArguments()).hasSize(1);
        assertThat(cell.getArguments()).containsEntry("0", new StringValue("mastercard"));
        assertThat(cell.getReturnValue().isPresent()).isFalse();
        assertThat(reader.getCurrentIndex()).isEqualTo(55);
    }


//    @Test
//    public void testFromSampleArticle() {
//        TextReader reader = new TextReader("    lorikeet.ecosphere.IssueDebitCard ('mastercard', -return=true)\n");
//
//        CellValue cell = deserializer.deserialize(reader).orPanic();
//        assertThat(cell.getClassName()).isEqualTo("lorikeet.ecosphere.IssueDebitCard");
//        assertThat(cell.getArguments()).hasSize(1);
//        assertThat(cell.getArguments()).containsEntry("paymentCompany", new StringValue("mastercard"));
//        assertThat(cell.getReturnValue().orPanic()).isEqualTo(new BoolValue(true));
//        assertThat(reader.getCurrentIndex()).isEqualTo(82);
//    }


}