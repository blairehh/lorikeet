package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.ecosphere.testing.data.HashValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashValueSerializerTest {

    private final HashValueSerializer serializer = new HashValueSerializer();

    @Test
    public void test() {
        assertThat(serializer.serialize(new HashValue("com.foo.Bar", "58745436")).orPanic())
            .isEqualTo("com.foo.Bar#58745436");
    }
}