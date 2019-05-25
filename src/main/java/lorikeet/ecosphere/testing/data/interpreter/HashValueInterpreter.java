package lorikeet.ecosphere.testing.data.interpreter;

import lorikeet.ecosphere.testing.data.HashValue;
import lorikeet.ecosphere.testing.data.NullValue;
import lorikeet.ecosphere.testing.data.Value;

public class HashValueInterpreter {

    public Value interpret(Object value) {
        if (value == null) {
            return new NullValue();
        }

        return new HashValue(value.getClass().getName(), String.valueOf(value.hashCode()));
    }
}
