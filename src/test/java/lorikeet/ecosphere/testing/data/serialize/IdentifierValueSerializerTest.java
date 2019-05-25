package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.ecosphere.testing.data.IdentifierValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentifierValueSerializerTest {

    private final IdentifierValueSerializer serializer = new IdentifierValueSerializer();

    @Test
    public void test() {
        assertThat(serializer.serialize(new IdentifierValue("com.foo.Bar")).orPanic()).isEqualTo("com.foo.Bar");
    }
}