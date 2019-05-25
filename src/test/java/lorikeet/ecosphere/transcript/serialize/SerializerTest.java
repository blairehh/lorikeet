package lorikeet.ecosphere.transcript.serialize;


import lorikeet.ecosphere.transcript.NumberValue;
import lorikeet.ecosphere.transcript.StringValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializerTest {

    private final Serializer serializer = new Serializer();

    @Test
    public void testString() {
        assertThat(serializer.serialize(new StringValue("hej"))).isEqualTo("'hej'");
    }

    @Test
    public void testNumber() {
        assertThat(serializer.serialize(new NumberValue(0))).isEqualTo("0");
    }
}