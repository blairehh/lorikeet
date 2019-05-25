package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.ecosphere.testing.data.StringValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringValueSerializerTest {

    private final StringValueSerializer serializer = new StringValueSerializer();

    @Test
    public void testString() {
        assertThat(serializer.serialize(new StringValue("hello")).orPanic()).isEqualTo("'hello'");
    }

}