package lorikeet.lobe.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.lobe.articletesting.data.AnyValue;
import lorikeet.lobe.articletesting.reader.TextReader;
import lorikeet.error.AnyValueDoesNotAcceptArguments;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AnyValueDeserializerTest {

    private final AnyValueDeserializer deserializer = new AnyValueDeserializer();

    @Test
    public void test() {
        TextReader reader = new TextReader("@any");
        AnyValue value = deserializer.deserialize(reader).orPanic();
        assertThat(value).isEqualTo(new AnyValue());
    }

    @Test
    public void testCanNotHaveArguments() {
        TextReader reader = new TextReader("@any(true)");
        Err<AnyValue> value = deserializer.deserialize(reader);
        assertThat(value.failedWith(new AnyValueDoesNotAcceptArguments())).isTrue();
    }

}