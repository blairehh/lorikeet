package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Seq;
import lorikeet.ecosphere.transcript.NumberValue;
import lorikeet.ecosphere.transcript.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListValueDeserializerTest {

    private final ListValueDeserializer deserializer = new ListValueDeserializer();

    @Test
    public void testListOfOne() {
        TextReader textReader = new TextReader("[1]", 0);
        assertThat(deserializer.deserialize(textReader).orPanic().getValues()).isEqualTo(Seq.of(new NumberValue(1)));
    }

    @Test
    public void testListOfTwoWithSpacing() {
        TextReader textReader = new TextReader("[ 1 , 2 ]", 0);

        assertThat(deserializer.deserialize(textReader).orPanic().getValues())
            .isEqualTo(Seq.of(new NumberValue(1), new NumberValue(2)));

        assertThat(textReader.getCurrentIndex()).isEqualTo(9);
    }

    @Test
    public void testListOfTwoNoSpacing() {
        TextReader textReader = new TextReader("[1,2]", 0);

        assertThat(deserializer.deserialize(textReader).orPanic().getValues())
            .isEqualTo(Seq.of(new NumberValue(1), new NumberValue(2)));

        assertThat(textReader.getCurrentIndex()).isEqualTo(5);
    }

}