package lorikeet.ecosphere.transcript.serialize;

import lorikeet.ecosphere.transcript.StringValue;

public class StringValueSerializer implements ValueSerializer<StringValue> {
    @Override
    public String serialize(StringValue value) {
        return String.format("'%s'", value.getValue());
    }
}
