package lorikeet.lobe.articletesting.data.interpreter;

import lorikeet.lobe.articletesting.data.HashValue;
import lorikeet.lobe.articletesting.data.NullValue;
import lorikeet.lobe.articletesting.data.Value;

public class HashValueInterpreter {

    public Value interpret(Object value) {
        if (value == null) {
            return new NullValue();
        }

        return new HashValue(value.getClass().getName(), String.valueOf(value.hashCode()));
    }
}
