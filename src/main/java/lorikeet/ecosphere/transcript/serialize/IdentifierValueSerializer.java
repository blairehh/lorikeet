package lorikeet.ecosphere.transcript.serialize;

import lorikeet.ecosphere.transcript.IdentifierValue;

public class IdentifierValueSerializer implements ValueSerializer<IdentifierValue> {
    @Override
    public String serialize(IdentifierValue value) {
        return value.getIdentifier();
    }
}
