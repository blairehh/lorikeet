package lorikeet.ecosphere.transcript.deserialize;


import lorikeet.ecosphere.transcript.HashValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashValueDeserializerTest {

    private HashValueDeserializer deserializer = new HashValueDeserializer();

    @Test
    public void testHashValue() {
        assertThat(deserializer.deserialize("lorikeet.Seq#576686").orPanic())
            .isEqualTo(new HashValue("lorikeet.Seq", "576686"));


        assertThat(deserializer.deserialize("lorikeet.Seq#0").orPanic())
            .isEqualTo(new HashValue("lorikeet.Seq", "0"));
    }


    @Test
    public void testNoHashValue() {
        assertThat(deserializer.deserialize("lorikeet.Seq#").isPresent()).isFalse();
    }

    @Test
    public void testJustClassName() {
        assertThat(deserializer.deserialize("lorikeet.Seq").isPresent()).isFalse();
    }

}