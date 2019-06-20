package lorikeet.ecosphere.articletesting.data.serialize;

import lorikeet.ecosphere.articletesting.data.StringValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringValueSerializerTest {

    private final StringValueSerializer serializer = new StringValueSerializer();

    @Test
    public void testString() {
        assertThat(serializer.serialize(new StringValue("hello")).orPanic()).isEqualTo("'hello'");
    }

}