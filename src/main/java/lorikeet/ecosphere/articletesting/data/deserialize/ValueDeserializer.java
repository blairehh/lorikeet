package lorikeet.ecosphere.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.articletesting.reader.TextReader;
import lorikeet.ecosphere.articletesting.data.Value;

public interface ValueDeserializer<ValueType extends Value> {
    Err<ValueType> deserialize(TextReader text);
}
