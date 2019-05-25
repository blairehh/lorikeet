package lorikeet.ecosphere.transcript.serialize;

import lorikeet.ecosphere.transcript.HashValue;

public class HashValueSerializer implements ValueSerializer<HashValue> {
    @Override
    public String serialize(HashValue value) {
        return String.format("%s#%s", value.getClassName(), value.getHashValue());
    }
}
