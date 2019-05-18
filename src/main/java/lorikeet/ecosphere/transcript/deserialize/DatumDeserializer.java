package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.Value;

public interface DatumDeserializer<ValueType extends Value> {
    Opt<ValueType> deserialize(String value);
}
