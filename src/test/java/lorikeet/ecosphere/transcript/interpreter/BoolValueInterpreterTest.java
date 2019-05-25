package lorikeet.ecosphere.transcript.interpreter;


import lorikeet.ecosphere.transcript.BoolValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoolValueInterpreterTest {

    private final BoolValueInterpreter interpreter = new BoolValueInterpreter();

    @Test
    public void testTrue() {
        assertThat(interpreter.interpret(true).orPanic()).isEqualTo(new BoolValue(true));
    }

    @Test
    public void testFalse() {
        assertThat(interpreter.interpret(false).orPanic()).isEqualTo(new BoolValue(false));
    }

}