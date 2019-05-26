package lorikeet.ecosphere.testing.data.generator;


import lorikeet.ecosphere.testing.data.NumberValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberValueGeneratorTest {

    private final NumberValueGenerator generator = new NumberValueGenerator();


    @Test
    public void testShort() {
        short value = 34;
        assertThat(generator.generate(Short.class, new NumberValue(34)).orPanic()).isEqualTo(value);
    }

    @Test
    public void testInt() {
        int value = 34;
        assertThat(generator.generate(Integer.class, new NumberValue(34)).orPanic()).isEqualTo(value);
    }

    @Test
    public void testLong() {
        long value = 34;
        assertThat(generator.generate(Long.class, new NumberValue(34)).orPanic()).isEqualTo(value);
    }

    @Test
    public void testFloat() {
        float value = 34;
        assertThat(generator.generate(Float.class, new NumberValue(34)).orPanic()).isEqualTo(value);
    }

    @Test
    public void testDouble() {
        double value = 34.885;
        assertThat(generator.generate(Double.class, new NumberValue(34.885)).orPanic()).isEqualTo(value);
    }

}