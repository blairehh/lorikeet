package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.TextReader;
import lorikeet.ecosphere.transcript.Value;

public interface ValueDeserializer<ValueType extends Value> {
    Opt<ValueType> deserialize(TextReader text);
}
