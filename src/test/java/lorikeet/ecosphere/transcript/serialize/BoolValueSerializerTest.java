package lorikeet.ecosphere.transcript.serialize;

import lorikeet.ecosphere.transcript.BoolValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoolValueSerializerTest {

    private final BoolValueSerializer serializer = new BoolValueSerializer();


    @Test
    public void testTrue() {
        assertThat(serializer.serialize(new BoolValue(true)).orPanic()).isEqualTo("true");
    }

    @Test
    public void testFalse() {
        assertThat(serializer.serialize(new BoolValue(false)).orPanic()).isEqualTo("false");
    }

}