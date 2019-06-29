package lorikeet.lobe.articletesting.data.interpreter;


import lorikeet.Seq;
import lorikeet.lobe.articletesting.data.ListValue;
import lorikeet.lobe.articletesting.data.NumberValue;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListValueInterpreterTest {

    private final ListValueInterpreter interpreter = new ListValueInterpreter();

    @Test
    public void testEmptyList() {
        assertThat(interpreter.interpret(List.of()).orPanic()).isEqualTo(new ListValue(Seq.empty()));
        assertThat(interpreter.interpret(Collections.emptyList()).orPanic()).isEqualTo(new ListValue(Seq.empty()));
        assertThat(interpreter.interpret(Seq.empty()).orPanic()).isEqualTo(new ListValue(Seq.empty()));
    }


    @Test
    public void testListWithOneItem() {
        assertThat(interpreter.interpret(List.of(1)).orPanic()).isEqualTo(new ListValue(Seq.of(new NumberValue(1))));
    }

    @Test
    public void testListWithTwoItems() {
        assertThat(interpreter.interpret(List.of(1, 4)).orPanic())
            .isEqualTo(new ListValue(Seq.of(new NumberValue(1), new NumberValue(4))));
    }

}