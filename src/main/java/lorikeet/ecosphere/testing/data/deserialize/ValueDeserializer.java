package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.TextReader;
import lorikeet.ecosphere.testing.data.Value;

public interface ValueDeserializer<ValueType extends Value> {
    Opt<ValueType> deserialize(TextReader text);
}
