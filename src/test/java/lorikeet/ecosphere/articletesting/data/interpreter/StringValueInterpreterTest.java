package lorikeet.ecosphere.articletesting.data.interpreter;


import lorikeet.ecosphere.articletesting.data.StringValue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringValueInterpreterTest {

    private final StringValueInterpreter interpreter = new StringValueInterpreter();

    @Test
    public void testString() {
        assertThat(interpreter.interpret("hello").orPanic()).isEqualTo(new StringValue("hello"));
    }

    @Test
    public void testChar() {
        assertThat(interpreter.interpret('A').orPanic()).isEqualTo(new StringValue("A"));
    }

    @Test
    public void testStringBuilder() {
        StringBuilder builder = new StringBuilder();
        builder.append("oi");
        assertThat(interpreter.interpret(builder).orPanic()).isEqualTo(new StringValue("oi"));
    }

}