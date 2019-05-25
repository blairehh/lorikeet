package lorikeet.ecosphere.transcript.serialize;

import lorikeet.ecosphere.transcript.Value;

public interface ValueSerializer<ValueType extends Value> {
    String serialize(ValueType value);
}
