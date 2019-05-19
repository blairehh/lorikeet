package lorikeet.ecosphere.transcript.deserialize;


import lorikeet.ecosphere.transcript.HashValue;
import lorikeet.ecosphere.transcript.TextReader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashValueDeserializerTest {

    private HashValueDeserializer deserializer = new HashValueDeserializer();

    @Test
    public void testHashValue() {
        assertThat(deserializer.deserialize(new TextReader("lorikeet.Seq#576686", 0)).orPanic())
            .isEqualTo(new HashValue("lorikeet.Seq", "576686"));
    }

    @Test
    public void testHasValueIsZero() {
        assertThat(deserializer.deserialize(new TextReader("lorikeet.Seq#0", 0)).orPanic())
            .isEqualTo(new HashValue("lorikeet.Seq", "0"));
    }


    @Test
    public void testNoHashValue() {
        assertThat(deserializer.deserialize(new TextReader("lorikeet.Seq#", 0)).isPresent()).isFalse();
    }

    @Test
    public void testJustClassName() {
        assertThat(deserializer.deserialize(new TextReader("lorikeet.Seq", 0)).isPresent()).isFalse();
    }

}