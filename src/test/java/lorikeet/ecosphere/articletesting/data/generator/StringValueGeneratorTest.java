package lorikeet.ecosphere.articletesting.data.generator;

import lorikeet.Err;
import lorikeet.ecosphere.articletesting.data.StringValue;
import lorikeet.error.StringValueMustBeOneCharacterToGenerateChar;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringValueGeneratorTest {

    private final StringValueGenerator generator = new StringValueGenerator();

    @Test
    public void testString() {
        assertThat(generator.generate(String.class, new StringValue("hello world")).orPanic()).isEqualTo("hello world");
    }

    @Test
    public void testCharacter() {
        assertThat(generator.generate(Character.class, new StringValue("A")).orPanic()).isEqualTo('A');
    }

    @Test
    public void testCharacterCanNotBe2OrMore() {
        Err<Character> generated = generator.generate(Character.class, new StringValue("ABC"));
        assertThat(generated.failedWith(new StringValueMustBeOneCharacterToGenerateChar())).isTrue();
    }

    @Test
    public void testCharacterCanNotBeEmpty() {
        Err<Character> generated = generator.generate(Character.class, new StringValue(""));
        assertThat(generated.failedWith(new StringValueMustBeOneCharacterToGenerateChar())).isTrue();
    }
}