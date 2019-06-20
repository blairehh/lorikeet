package lorikeet.ecosphere.articletesting.data.generator;

import lorikeet.ecosphere.articletesting.data.BoolValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoolValueGeneratorTest {

    private final BoolValueGenerator generator = new BoolValueGenerator();

    @Test
    public void testTrue() {
        assertThat(generator.generate(Boolean.class, new BoolValue(true)).orPanic()).isEqualTo(true);
    }

    @Test
    public void testFalse() {
        assertThat(generator.generate(Boolean.class, new BoolValue(false)).orPanic()).isEqualTo(false);
    }
}