package lorikeet.ecosphere.transcript.serialize;

import lorikeet.ecosphere.transcript.BoolValue;

public class BoolValueSerializer implements ValueSerializer<BoolValue> {
    @Override
    public String serialize(BoolValue value) {
        return String.valueOf(value.getValue());
    }
}
