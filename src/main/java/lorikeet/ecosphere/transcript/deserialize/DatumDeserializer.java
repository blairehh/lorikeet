package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.TextReader;
import lorikeet.ecosphere.transcript.Value;

// @TODO rename to ValueDeserializer
public interface DatumDeserializer<ValueType extends Value> {
    Opt<ValueType> deserialize(TextReader text);
}
