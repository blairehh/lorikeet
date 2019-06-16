package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.ecosphere.testing.data.Value;

public interface ValueDeserializer<ValueType extends Value> {
    Err<ValueType> deserialize(TextReader text);
}
