package lorikeet.ecosphere.transcript.serialize;

import lorikeet.ecosphere.transcript.NumberValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberValueSerializerTest {

    private final NumberValueSerializer serializer = new NumberValueSerializer();

    @Test
    public void testWholeNumber() {
        assertThat(serializer.serialize(new NumberValue(4554))).isEqualTo("4554");
    }

    @Test
    public void testWholeNegativeNumber() {
        assertThat(serializer.serialize(new NumberValue(-56))).isEqualTo("-56");
    }

    @Test
    public void testFraction() {
        assertThat(serializer.serialize(new NumberValue(56.55))).isEqualTo("56.55");
    }

}