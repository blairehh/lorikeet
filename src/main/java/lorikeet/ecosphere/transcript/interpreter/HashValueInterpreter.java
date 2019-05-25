package lorikeet.ecosphere.transcript.interpreter;

import lorikeet.ecosphere.transcript.HashValue;
import lorikeet.ecosphere.transcript.NullValue;
import lorikeet.ecosphere.transcript.Value;

public class HashValueInterpreter {

    public Value interpret(Object value) {
        if (value == null) {
            return new NullValue();
        }

        return new HashValue(value.getClass().getName(), String.valueOf(value.hashCode()));
    }
}
