package lorikeet.ecosphere.transcript.interpreter;


import lorikeet.Dict;
import lorikeet.ecosphere.transcript.MapValue;
import lorikeet.ecosphere.transcript.NumberValue;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MapValueInterpreterTest {

    private final MapValueInterpreter interpreter = new MapValueInterpreter();

    @Test
    public void testEmptyMap() {
        assertThat(interpreter.interpret(Collections.emptyMap()).orPanic()).isEqualTo(new MapValue(Dict.empty()));
    }

    @Test
    public void testMapWithOneItem() {
        assertThat(interpreter.interpret(Map.of(1,1)).orPanic())
            .isEqualTo(new MapValue(Dict.of(new NumberValue(1),new NumberValue(1))));
    }

    @Test
    public void testMapWithTwoItems() {
        assertThat(interpreter.interpret(Map.of(1,1, 2, 2)).orPanic())
            .isEqualTo(new MapValue(Dict.of(new NumberValue(1),new NumberValue(1), new NumberValue(2),new NumberValue(2))));
    }

    @Test
    public void testDifferentTypesOfMaps() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(1,1);

        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1, 1);

        MapValue value = new MapValue(Dict.of(new NumberValue(1), new NumberValue(1)));

        assertThat(interpreter.interpret(hashMap).orPanic()).isEqualTo(value);
        assertThat(interpreter.interpret(linkedHashMap).orPanic()).isEqualTo(value);
    }

}