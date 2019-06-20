package lorikeet.ecosphere.articletesting.data.interpreter;

import lorikeet.ecosphere.articletesting.data.HashValue;
import lorikeet.ecosphere.articletesting.data.NullValue;
import lorikeet.ecosphere.articletesting.data.Value;

public class HashValueInterpreter {

    public Value interpret(Object value) {
        if (value == null) {
            return new NullValue();
        }

        return new HashValue(value.getClass().getName(), String.valueOf(value.hashCode()));
    }
}
