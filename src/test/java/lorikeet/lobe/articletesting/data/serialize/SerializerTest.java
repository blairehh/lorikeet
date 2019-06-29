package lorikeet.lobe.articletesting.data.serialize;


import lorikeet.lobe.articletesting.data.NumberValue;
import lorikeet.lobe.articletesting.data.StringValue;
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