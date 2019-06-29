package lorikeet.lobe.articletesting.data.interpreter;

import lorikeet.lobe.articletesting.data.NumberValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberValueInterpreterTest {

    private final NumberValueInterpreter interpreter = new NumberValueInterpreter();

    @Test
    public void testInt() {
        assertThat(interpreter.interpret(3).orPanic()).isEqualTo(new NumberValue(3));
    }

    @Test
    public void testLong() {
        assertThat(interpreter.interpret(3L).orPanic()).isEqualTo(new NumberValue(3L));
    }

    @Test
    public void testDouble() {
        assertThat(interpreter.interpret(3.0).orPanic()).isEqualTo(new NumberValue(3L));
    }
}