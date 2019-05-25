package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.Dict;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.CellValue;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.data.ListValue;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.ObjectValue;
import lorikeet.ecosphere.testing.data.StringValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CellValueSerializerTest {

    private final CellValueSerializer serializer = new CellValueSerializer();

    @Test
    public void testNoArgsNoReturnNoException() {
        CellValue cell = new CellValue("com.foo.Bar", Dict.empty(), null, null);
        assertThat(serializer.serialize(cell).orPanic()).isEqualTo("<com.foo.Bar >");
    }

    @Test
    public void testOneArg() {
        CellValue cell = new CellValue("com.foo.Bar", Dict.of("name", new StringValue("Bob")), null, null);
        assertThat(serializer.serialize(cell).orPanic()).isEqualTo("<com.foo.Bar name='Bob' >");
    }

    @Test
    public void testTwoArgs() {
        CellValue cell = new CellValue(
            "com.foo.Bar",
            Dict.of("name", new StringValue("Bob"), "height", new NumberValue(191)),
            null,
            null
        );
        assertThat(serializer.serialize(cell).orPanic()).isEqualTo("<com.foo.Bar height=191 name='Bob' >");
    }

    @Test
    public void testReturnValue() {
        CellValue cell = new CellValue(
            "com.foo.Bar",
            Dict.empty(),
            null,
            new BoolValue(true)
        );
        assertThat(serializer.serialize(cell).orPanic()).isEqualTo("<com.foo.Bar -return=true >");
    }

    @Test
    public void testReturnValueWithArg() {
        CellValue cell = new CellValue(
            "com.foo.Bar",
            Dict.of("numbers", new ListValue(new NumberValue(1), new NumberValue(2))),
            null,
            new BoolValue(true)
        );
        assertThat(serializer.serialize(cell).orPanic()).isEqualTo("<com.foo.Bar numbers=[1, 2] -return=true >");
    }

    @Test
    public void textExceptionThrown() {
        CellValue cell = new CellValue(
            "com.foo.Bar",
            Dict.empty(),
            new IdentifierValue("java.lang.NullPointerException"),
            null
        );
        assertThat(serializer.serialize(cell).orPanic()).isEqualTo("<com.foo.Bar -exception=java.lang.NullPointerException >");
    }

    @Test
    public void textExceptionThrownWithArg() {
        CellValue cell = new CellValue(
            "com.foo.Bar",
            Dict.of("data", new ObjectValue("com.foo.Baz", Dict.of("rank", new NumberValue(1)))),
            new IdentifierValue("java.lang.NullPointerException"),
            null
        );
        assertThat(serializer.serialize(cell).orPanic()).isEqualTo("<com.foo.Bar data=com.foo.Baz(rank=1) -exception=java.lang.NullPointerException >");
    }

}