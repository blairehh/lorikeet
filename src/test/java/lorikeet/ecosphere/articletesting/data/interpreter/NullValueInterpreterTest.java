package lorikeet.ecosphere.articletesting.data.interpreter;


import lorikeet.ecosphere.articletesting.data.NullValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NullValueInterpreterTest {

    private final NullValueInterpreter interpreter = new NullValueInterpreter();


    @Test
    public void testNull() {
        assertThat(interpreter.interpret(null).orPanic()).isEqualTo(new NullValue());
    }

    @Test
    public void testNotNull() {
        assertThat(interpreter.interpret("Hello").isPresent()).isFalse();
    }

}