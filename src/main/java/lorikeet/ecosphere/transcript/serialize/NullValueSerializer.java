package lorikeet.ecosphere.transcript.serialize;

import lorikeet.ecosphere.transcript.NullValue;

public class NullValueSerializer implements ValueSerializer<NullValue> {
    @Override
    public String serialize(NullValue value) {
        return "null";
    }
}
